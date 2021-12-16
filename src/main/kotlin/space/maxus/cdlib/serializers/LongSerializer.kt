package space.maxus.cdlib.serializers

import space.maxus.cdlib.FieldSerializer
import space.maxus.cdlib.fields.LongField
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

object LongSerializer : FieldSerializer<Long, LongField> {
    override fun deserialize(from: ByteArrayInputStream): LongField {
        val buffer = ByteArray(8)
        for (i in 0..7) {
            buffer[i] = from.read().toByte()
        }
        return LongField(ByteBuffer.wrap(buffer).long)
    }
}