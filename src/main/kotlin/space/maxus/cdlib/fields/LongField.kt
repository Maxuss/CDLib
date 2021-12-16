package space.maxus.xai.save.fields

import space.maxus.xai.save.SaveField
import java.nio.ByteBuffer

class LongField(override var value: Long) : SaveField<Long>() {
    override fun serialize(): ByteArray {
        val bytes: ByteArray = ByteBuffer.allocate(8).putLong(value).array()
        return byteArrayOf(0x04, *bytes)
    }
}