package space.maxus.cdlib.fields

import space.maxus.cdlib.SaveField
import java.nio.ByteBuffer

class LongField(override var value: Long) : SaveField<Long>() {
    override fun serialize(): ByteArray {
        val bytes: ByteArray = ByteBuffer.allocate(8).putLong(value).array()
        return byteArrayOf(0x04, *bytes)
    }
}