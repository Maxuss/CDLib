package space.maxus.xai.save

import space.maxus.xai.save.serializers.*

enum class FieldType(val pos: Int, val serializer: FieldSerializer<*, *>) {
    NULL(0x00, NullSerializer),
    INT(0x01, IntSerializer),
    FLOAT(0x02, FloatSerializer),
    DOUBLE(0x03, DoubleSerializer),
    LONG(0x04, LongSerializer),
    STRING(0x05, StringSerializer),
    PAIR(0x06, PairSerializer),
    LIST(0x07, ListSerializer),
    TUPLE(0x08, TupleSerializer),
    ;
    companion object {
        fun findByInt(seek: Int): FieldType {
            val filtered = values().filter { value -> value.pos == seek }
            if(filtered.isEmpty())
                return NULL
            return filtered[0]
        }
    }
}