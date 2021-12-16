package space.maxus.cdlib

import java.io.ByteArrayInputStream

interface FieldSerializer<R, T : SaveField<R>> {
    fun deserialize(from: ByteArrayInputStream): T?
}