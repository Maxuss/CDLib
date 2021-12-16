package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.fields.StringField
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

object StringSerializer : FieldSerializer<String, StringField> {
    override fun deserialize(from: ByteArrayInputStream): StringField {
        val intBuffer = byteArrayOf(from.read().toByte(), from.read().toByte(), from.read().toByte(), from.read().toByte())
        val length = ByteBuffer.wrap(intBuffer).int
        val stringBuffer = ByteArray(length)
        for(i in 0 until length){
            stringBuffer[i] = from.read().toByte()
        }
        return StringField(String(stringBuffer, Charsets.UTF_8))
    }
}