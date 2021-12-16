package space.maxus.cdlib.typed

import space.maxus.cdlib.FieldType

annotation class Field(val type: FieldType)
annotation class Serialize(val sig: Byte = 0xF)

annotation class RepresentedBy(val repr: Array<String>)