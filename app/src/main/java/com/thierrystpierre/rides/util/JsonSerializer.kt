package com.thierrystpierre.rides.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import java.sql.Timestamp

@OptIn(ExperimentalSerializationApi::class)
val JsonSerializer = Json {
//      prettyPrint = true
//      useArrayPolymorphism = true

    encodeDefaults = true
    allowStructuredMapKeys = true
    isLenient = true
    ignoreUnknownKeys = true
    explicitNulls = false

    serializersModule = SerializersModule {
        contextual(Number::class) { NumberAsStringSerializer }
        contextual(Timestamp::class) { TimestampSerializer }
    }
}

object NumberAsStringSerializer : KSerializer<Number?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Number", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Number?) {
        "$value".run {
            toIntOrNull()?.let { encoder.encodeInt(it) }
                ?: toLongOrNull()?.let { encoder.encodeLong(it) }
                ?: toDoubleOrNull()?.let { encoder.encodeDouble(it) }
        }
    }

    override fun deserialize(decoder: Decoder): Number? {
        return decoder.decodeString().run {
            toIntOrNull()
                ?: toLongOrNull()
                ?: toDoubleOrNull()
        }
    }
}

object TimestampSerializer : KSerializer<Timestamp?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Timestamp", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Timestamp?) {
        value?.let {
            encoder.encodeLong(it.time)
        }
    }

    override fun deserialize(decoder: Decoder): Timestamp {
        return decoder.decodeLong().run {
            Timestamp(this)
        }
    }
}

fun primitiveMapOf(vararg pairs: Pair<String, Any>) = pairs.associate { pairToPrimitive(it) }

@OptIn(ExperimentalSerializationApi::class)
private fun pairToPrimitive(pair: Pair<String, Any>) = when (pair.second) {
    is JsonElement -> pair.first to pair.second as JsonElement
    is String -> pair.first to JsonPrimitive(pair.second as String)
    is Boolean -> pair.first to JsonPrimitive(pair.second as Boolean)
    is Number -> pair.first to JsonPrimitive(pair.second as Number)
    else -> pair.first to JsonPrimitive(null)
}