package space.maxus.cdlib

import space.maxus.cdlib.fields.PairField

@Suppress("UNCHECKED_CAST")
class MappedSaveFile : SaveFile() {
    var mappedFields: MutableList<PairField<*>> = mutableListOf()

    override var fields: Array<SaveField<*>?>
        get() = mappedFields.toTypedArray()
        set(value) {
            mappedFields = value.toMutableList() as? MutableList<PairField<*>>
                ?: error("You should not modify vanilla fields of save file if you don't know what you are doing!")
        }

    operator fun get(key: String): PairField<*> {
        val filtered = mappedFields.filter { it.value.first.value == key }
        if (filtered.isEmpty())
            error("There is no value for key $key!")
        return filtered[0]
    }

    operator fun set(key: String, value: SaveField<*>) {
        val filtered = mappedFields.filter { it.value.first.value == key }
        if (filtered.isNotEmpty()) {
            val index = mappedFields.indexOf(filtered[0])
            mappedFields[index] = PairField(key to value)
        }
        mappedFields.add(PairField(key to value))
    }

    inline fun <reified T : Any> receive(key: String) = (this[key] as PairField<SaveField<T>>).value.second
    inline fun <reified T : Any> put(key: String, value: SaveField<T>) = this.set(key, value)

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append('{')
        builder.append('\n')
        for (pair in mappedFields.map { it.value }) {
            builder.append("\"${pair.first.value}\"")
            builder.append(" = ")
            builder.append("${pair.second}")
            builder.append(",\n")
        }
        builder.removeSuffix(",\n")
        builder.append("}")
        return builder.toString()
    }

    companion object {
        fun deserialize(from: ByteArray): MappedSaveFile {
            val deserialized = SaveFile.deserialize(from)
            var hash = 0
            val map = MappedSaveFile()
            for (field: SaveField<*>? in deserialized.fields) {
                var paired: PairField<*>
                if (field == null)
                    continue
                if (field !is PairField<*>) {
                    paired = PairField("$hash" to field)
                    hash++
                } else paired = field
                map.mappedFields.add(paired)
            }
            return map
        }
    }
}