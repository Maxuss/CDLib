package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.fields.DoubleField
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

object DoubleSerializer : FieldSerializer<Double, DoubleField> {
    override fun deserialize(from: ByteArrayInputStream): DoubleField {
        val buffer = ByteArray(8)
        for (i in 0..7) {
            buffer[i] = from.read().toByte()
        }
        return DoubleField(ByteBuffer.wrap(buffer).double)
    }
}