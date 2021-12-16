package space.maxus.xai.save.fields

import space.maxus.xai.save.SaveField

class NullField : SaveField<Unit>() {
    override var value: Unit = Unit
    override fun serialize(): ByteArray {
        return byteArrayOf(0x00)
    }
}