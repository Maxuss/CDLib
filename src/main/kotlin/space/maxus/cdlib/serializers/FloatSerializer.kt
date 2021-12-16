package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.fields.FloatField
import java.io.ByteArrayInputStream


object FloatSerializer : FieldSerializer<Float, FloatField> {
    override fun deserialize(from: ByteArrayInputStream): FloatField {
        val intBits: Int =
            from.read() shl 24 or (from.read() and 0xFF shl 16) or (from.read() and 0xFF shl 8) or (from.read() and 0xFF)
        return FloatField(java.lang.Float.intBitsToFloat(intBits))
    }
}