package space.maxus.xai.save.fields

import space.maxus.xai.save.SaveField
import java.nio.ByteBuffer

class DoubleField(override var value: Double) : SaveField<Double>() {
    override fun serialize(): ByteArray {
        val bytes = ByteArray(8)
        ByteBuffer.wrap(bytes).putDouble(value)
        return byteArrayOf(0x03, *bytes)
    }
}