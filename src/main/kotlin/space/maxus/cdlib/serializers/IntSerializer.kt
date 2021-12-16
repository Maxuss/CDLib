package space.maxus.cdlib.serializers

import space.maxus.cdlib.FieldSerializer
import space.maxus.cdlib.fields.IntField
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