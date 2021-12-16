@file:Suppress("UNCHECKED_CAST")

package space.maxus.cdlib

import space.maxus.cdlib.fields.NullField
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

open class SaveFile {
    open var fields: Array<SaveField<*>?> = arrayOf()

    fun serialize(): ByteArray {
        val stream = ByteArrayOutputStream()
        val bytes = stream.use {
            // magic int
            it.write(127)
            // fields amount
            it.write(fields.size)

            for (field in fields) {
                // padding
                it.write(1)
                // field data
                val bytes = field?.serialize()
                if (bytes == null) {
                    it.write(0x00)
                    continue
                }
                it.write(bytes)
            }
            // magic int
            it.write(127)
            return@use it.toByteArray()
        }
        return bytes
    }

    operator fun get(pos: Int) = fields[pos]
    operator fun set(pos: Int, value: SaveField<*>) {
        fields[pos] = value
    }

    inline fun <reified T : Any> receive(pos: Int) = fields[pos] as SaveField<T>
    inline fun <reified T : Any> put(pos: Int, value: SaveField<T>) {
        fields[pos] = value
    }

    inline fun <reified T : SaveField<R>, R : Any> add(value: T) {
        fields += value
    }

    operator fun plusAssign(value: SaveField<*>) {
        fields += value
    }

    companion object {
        fun deserialize(data: ByteArray): SaveFile {
            val stream = ByteArrayInputStream(data)
            val save = stream.use {
                if (it.read() != 127)
                    throw IOException("Corrupted save file format! Could not find magic number at the top!")

                // size of array
                val size = it.read()
                val save = SaveFile()
                save.fields = arrayOfNulls(size)
                var cur = it.read()
                var pos = 0
                while (cur != 127) {
                    // padding
                    if (cur == 1) {
                        val intType = it.read()
                        val type = FieldType.findByInt(intType)
                        val serialized = type.serializer.deserialize(it)
                        save.fields[pos] = serialized ?: NullField()
                    }
                    cur = it.read()
                    pos++
                }

                return@use save
            }

            return save
        }
    }
}