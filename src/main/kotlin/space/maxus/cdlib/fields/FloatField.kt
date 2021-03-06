package space.maxus.cdlib.fields

import space.maxus.cdlib.SaveField

class FloatField(override var value: Float) : SaveField<Float>() {
    override fun serialize(): ByteArray {
        val intBits = java.lang.Float.floatToIntBits(value)
        return byteArrayOf(0x02,
            (intBits shr 24).toByte(),
            (intBits shr 16).toByte(),
            (intBits shr 8).toByte(),
            intBits.toByte()
        )
    }
}