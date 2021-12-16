@file:Suppress("UNCHECKED_CAST", "EXPERIMENTAL_IS_NOT_ENABLED")

package space.maxus.cdlib.typed

import space.maxus.cdlib.FieldType
import space.maxus.cdlib.SaveField
import space.maxus.cdlib.fields.*
import java.io.ByteArrayInputStream
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaSetter

object TypeSerializer {
    @OptIn(ExperimentalStdlibApi::class)
    fun <T : Any> serialize(obj: T, klass: KClass<T>): ByteArray {
        val typeAnnotations = klass.findAnnotations<Serialize>()
        val representedBy = klass.findAnnotations<RepresentedBy>()
        val data: MutableList<Byte> = mutableListOf(0x09)
        if (typeAnnotations.isEmpty() && representedBy.isNotEmpty()) {
            val reprs = representedBy[0].repr
            val members = klass.declaredMemberProperties.filter { f -> reprs.contains(f.name) }
            data.add(0x07)
            data.add(members.size.toByte())
            for (field in members) {
                val o = field.get(obj)
                val t = field.findAnnotation<Field>()?.type
                val fieldData = getFieldByType(t ?: FieldType.NULL, o)
                data.addAll(fieldData.serialize().toList())
            }

            return data.toByteArray()
        }
        val sig = if (typeAnnotations.isEmpty()) 0xF else typeAnnotations[0].sig
        val fields = klass.declaredMemberProperties.filter { f -> f.findAnnotation<Field>() != null }
        data.add(sig)
        data.add(fields.size.toByte())
        for (field in fields) {
            val o = field.get(obj)
            val t = field.findAnnotation<Field>()?.type
            val fieldData = getFieldByType(t ?: FieldType.NULL, o)
            data.addAll(fieldData.serialize().toList())
        }

        return data.toByteArray()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun <T : Any> deserialize(data: ByteArrayInputStream, klass: KClass<T>): T {
        if (data.read() != 0x09)
            throw Exception("Invalid file data provided!")
        if (klass.isAbstract || klass.java.isInterface)
            throw Exception("Could not deserialize data! The provided type of ${klass.qualifiedName} can not be instantiated!")
        val constructors = klass.constructors.filter { it.parameters.isEmpty() }
        if (constructors.isEmpty())
            throw Exception("Could not deserialize data! The provided type of ${klass.qualifiedName} can not be instantiated!")
        val constructor = constructors[0]
        val instance = constructor.call()

        // signature
        data.read()

        if (klass.hasAnnotation<RepresentedBy>()) {
            // eat field size amount
            data.read()
            val reprs = klass.findAnnotation<RepresentedBy>()!!.repr
            val members = klass.declaredMemberProperties.filter { f -> reprs.contains(f.name) }
                .filterIsInstance<KMutableProperty<*>>()

            for (member in members) {
                member.javaSetter?.invoke(instance,
                    FieldType.findByInt(data.read()).serializer.deserialize(data)?.value)
            }

            return instance
        }

        val size = data.read()
        val fields = klass.memberProperties.filterIsInstance<KMutableProperty<*>>()
        if (fields.size < size)
            throw Exception("Invalid amount of fields for serialization provided!")
        for (i in 0 until size) {
            val type = data.read()
            val ft = FieldType.findByInt(type)
            val fData = ft.serializer.deserialize(data)?.value
            val current = fields[i]
            current.javaSetter?.invoke(instance, fData)
        }
        return instance
    }
}

inline fun <reified T> getFieldByType(t: FieldType, data: T) = when (t) {
    FieldType.INT -> IntField(data as Int)
    FieldType.STRING -> StringField(data as String)
    FieldType.DOUBLE -> DoubleField(data as Double)
    FieldType.FLOAT -> FloatField(data as Float)
    FieldType.LIST -> TupleField(data as List<SaveField<*>>)
    FieldType.LONG -> LongField(data as Long)
    FieldType.NULL -> NullField()
    FieldType.PAIR -> PairField(data as Pair<String, SaveField<*>>)
    FieldType.TUPLE -> TupleField(data as List<SaveField<*>>)
}