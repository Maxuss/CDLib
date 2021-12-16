package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.FieldType
import space.maxus.xai.save.SaveField
import space.maxus.xai.save.fields.PairField
import space.maxus.xai.save.fields.StringField
import java.io.ByteArrayInputStream

object PairSerializer: FieldSerializer<Pair<StringField, SaveField<*>>, PairField<SaveField<*>>> {
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