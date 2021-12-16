package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.FieldType
import space.maxus.xai.save.SaveField
import space.maxus.xai.save.fields.ListField
import space.maxus.xai.save.fields.NullField
import java.io.ByteArrayInputStream

object ListSerializer : FieldSerializer<List<SaveField<*>>, ListField<SaveField<*>>> {
    override fun deserialize(from: ByteArrayInputStream): ListField<SaveField<*>> {
        val type = FieldType.findByInt(from.read())
        val size = from.read()
        val out = mutableListOf<SaveField<*>>()
        for(i in 0 until size) {
            val des = type.serializer.deserialize(from)
            out.add(des ?: NullField())
        }
        return ListField(out, type)
    }
}