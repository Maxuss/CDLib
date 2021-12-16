package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.FieldType
import space.maxus.xai.save.SaveField
import space.maxus.xai.save.fields.TupleField
import space.maxus.xai.save.fields.NullField
import java.io.ByteArrayInputStream

object TupleSerializer : FieldSerializer<List<SaveField<*>>, TupleField> {
    override fun deserialize(from: ByteArrayInputStream): TupleField {
        val size = from.read()
        val out = mutableListOf<SaveField<*>>()
        for(i in 0 until size) {
            val type = from.read()
            val typed = FieldType.findByInt(type)
            val de = typed.serializer.deserialize(from)
            out.add(de ?: NullField())
        }
        return TupleField(out)
    }
}