package space.maxus.xai.save.typed

import space.maxus.xai.save.FieldType

annotation class Field(val type: FieldType)
annotation class Serialize(val sig: Byte = 0xF)

annotation class RepresentedBy(val repr: Array<String>)