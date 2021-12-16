package space.maxus.cdlib.fields

import space.maxus.cdlib.SaveField

class TupleField(override var value: List<SaveField<*>>) : SaveField<List<SaveField<*>>>() {
    override fun serialize(): ByteArray {
        val size = value.size
        val mapped = value.map { it.serialize() }
        val list = mutableListOf(0x08, size.toByte())
        for (ele in mapped) {
            list.addAll(ele.toList())
        }
        return list.toByteArray()
    }

    override fun toString() = "Tuple${value.joinToString(prefix = "(", postfix = ")")}"
}