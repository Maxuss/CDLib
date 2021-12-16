package space.maxus.xai.save.fields

import space.maxus.xai.save.SaveField
import java.nio.ByteBuffer

class IntField(override var value: Int) : SaveField<Int>() {
    override fun serialize(): ByteArray {
        val bytes: ByteArray = ByteBuffer.allocate(4).putInt(value).array()
        return byteArrayOf(0x01, *bytes)
    }
}