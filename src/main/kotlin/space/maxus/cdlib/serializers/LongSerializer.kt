package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.fields.LongField
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