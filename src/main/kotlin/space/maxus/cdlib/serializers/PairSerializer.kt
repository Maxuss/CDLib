package space.maxus.cdlib.serializers

import space.maxus.cdlib.FieldSerializer
import space.maxus.cdlib.FieldType
import space.maxus.cdlib.SaveField
import space.maxus.cdlib.fields.PairField
import space.maxus.cdlib.fields.StringField
import java.io.ByteArrayInputStream

object PairSerializer : FieldSerializer<Pair<StringField, SaveField<*>>, PairField<SaveField<*>>> {
    override fun deserialize(from: ByteArrayInputStream): PairField<SaveField<*>> {
        // string ordinal
        from.read()

        val key = StringSerializer.deserialize(from)
        // padding
        from.read()
        val ordinal = from.read()
        val value = FieldType.findByInt(ordinal).serializer.deserialize(from)
        return PairField(key to value!!)
    }
}