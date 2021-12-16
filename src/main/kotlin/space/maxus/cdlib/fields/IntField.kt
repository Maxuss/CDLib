package space.maxus.cdlib.fields

import space.maxus.cdlib.SaveField
import java.nio.ByteBuffer

class IntField(override var value: Int) : SaveField<Int>() {
    override fun serialize(): ByteArray {
        val bytes: ByteArray = ByteBuffer.allocate(4).putInt(value).array()
        return byteArrayOf(0x01, *bytes)
    }
}