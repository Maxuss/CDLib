package space.maxus.xai.save.fields

import space.maxus.xai.save.SaveField
import java.nio.ByteBuffer

class StringField(override var value: String) : SaveField<String>() {
    override fun serialize(): ByteArray {
        val buffer = ByteBuffer.allocate(4).putInt(value.length).array()
        return byteArrayOf(0x05, *buffer, *value.toByteArray(charset = Charsets.UTF_8))
    }

    override fun toString(): String = "\"$value\""
}