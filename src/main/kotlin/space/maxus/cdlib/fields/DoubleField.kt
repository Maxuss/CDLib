package space.maxus.cdlib.fields

import space.maxus.cdlib.SaveField
import java.nio.ByteBuffer

class DoubleField(override var value: Double) : SaveField<Double>() {
    override fun serialize(): ByteArray {
        val bytes = ByteArray(8)
        ByteBuffer.wrap(bytes).putDouble(value)
        return byteArrayOf(0x03, *bytes)
    }
}