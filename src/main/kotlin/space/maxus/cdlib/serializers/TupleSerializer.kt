package space.maxus.cdlib.serializers

import space.maxus.cdlib.FieldSerializer
import space.maxus.cdlib.FieldType
import space.maxus.cdlib.SaveField
import space.maxus.cdlib.fields.NullField
import space.maxus.cdlib.fields.TupleField
import java.io.ByteArrayInputStream

object TupleSerializer : FieldSerializer<List<SaveField<*>>, TupleField> {
    override fun deserialize(from: ByteArrayInputStream): TupleField {
        val size = from.read()
        val out = mutableListOf<SaveField<*>>()
        for (i in 0 until size) {
            val type = from.read()
            val typed = FieldType.findByInt(type)
            val de = typed.serializer.deserialize(from)
            out.add(de ?: NullField())
        }
        return TupleField(out)
    }
}