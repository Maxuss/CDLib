package space.maxus.xai.save

import java.io.ByteArrayInputStream

interface FieldSerializer<R, T: SaveField<R>> {
    fun deserialize(from: ByteArrayInputStream) : T?
}