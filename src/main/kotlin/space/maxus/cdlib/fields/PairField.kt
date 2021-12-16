package space.maxus.xai.save.fields

import space.maxus.xai.save.SaveField

fun <R: SaveField<*>> PairField(value: Pair<String, R>) = PairField(StringField(value.first) to value.second)

class PairField<R: SaveField<*>>(override var value: Pair<StringField, R>) : SaveField<Pair<StringField, R>>() {
    override fun serialize(): ByteArray {
        return byteArrayOf(0x06, *value.first.serialize(), 127, *value.second.serialize())
    }

    override fun toString() = "Pair{${value.first}, ${value.second}}"
}