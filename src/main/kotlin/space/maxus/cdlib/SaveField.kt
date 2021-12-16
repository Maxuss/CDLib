package space.maxus.xai.save

abstract class SaveField<T> {
    abstract var value: T

    abstract fun serialize() : ByteArray

    override fun toString() = value.toString()
}