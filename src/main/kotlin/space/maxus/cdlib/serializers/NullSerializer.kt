package space.maxus.cdlib.serializers

import space.maxus.cdlib.FieldSerializer
import space.maxus.cdlib.fields.NullField
import java.io.ByteArrayInputStream

object NullSerializer : FieldSerializer<Unit, NullField> {
    override fun deserialize(from: ByteArrayInputStream): NullField = NullField()
}