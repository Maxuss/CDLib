package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.fields.IntField
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

object IntSerializer : FieldSerializer<Int, IntField> {
    override fun deserialize(from: ByteArrayInputStream): IntField {
        val buffer = ByteArray(4)
        for (i in 0..3) {
            buffer[i] = from.read().toByte()
        }
        return IntField(ByteBuffer.wrap(buffer).int)
    }
}