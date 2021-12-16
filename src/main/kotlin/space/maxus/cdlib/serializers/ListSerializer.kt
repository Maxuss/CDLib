package space.maxus.cdlib.serializers

import space.maxus.cdlib.FieldSerializer
import space.maxus.cdlib.FieldType
import space.maxus.cdlib.SaveField
import space.maxus.cdlib.fields.ListField
import space.maxus.cdlib.fields.NullField
import java.io.ByteArrayInputStream

object ListSerializer : FieldSerializer<List<SaveField<*>>, ListField<SaveField<*>>> {
    override fun deserialize(from: ByteArrayInputStream): ListField<SaveField<*>> {
        val type = FieldType.findByInt(from.read())
        val size = from.read()
        val out = mutableListOf<SaveField<*>>()
        for (i in 0 until size) {
            val des = type.serializer.deserialize(from)
            out.add(des ?: NullField())
        }
        return ListField(out, type)
    }
}