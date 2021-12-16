package space.maxus.cdlib.fields

import space.maxus.cdlib.SaveField

class NullField : SaveField<Unit>() {
    override var value: Unit = Unit
    override fun serialize(): ByteArray {
        return byteArrayOf(0x00)
    }
}