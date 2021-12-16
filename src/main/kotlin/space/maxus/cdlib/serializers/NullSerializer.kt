package space.maxus.xai.save.serializers

import space.maxus.xai.save.FieldSerializer
import space.maxus.xai.save.fields.NullField
import java.io.ByteArrayInputStream

object NullSerializer: FieldSerializer<Unit, NullField> {
    override fun deserialize(from: ByteArrayInputStream): NullField = NullField()
}