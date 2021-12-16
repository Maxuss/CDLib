package space.maxus.cdlib.fields

import space.maxus.cdlib.FieldType
import space.maxus.cdlib.SaveField

class ListField<T : SaveField<*>>(override var value: List<T>, private val type: FieldType) : SaveField<List<T>>() {
    override fun serialize(): ByteArray {
        if (value.isNotEmpty()) {
            val standard = value[0].value?.javaClass
            if (!value.all { it.value?.javaClass == standard })
                error("ListField can not have different types inside itself!")
        }
        val size = value.size
        val mapped = value.map { it.serialize() }
        val type = this.type.pos
        val list = mutableListOf(0x07, type.toByte(), size.toByte())
        for (ele in mapped) {
            list.addAll(ele.toList().subList(1, ele.size))
        }
        return list.toByteArray()
    }

    override fun toString() = "List${value.joinToString(prefix = "[", postfix = "]")}"
}