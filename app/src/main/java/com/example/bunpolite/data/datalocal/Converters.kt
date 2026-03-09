package com.example.bunpolite.data.datalocal

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String): List<String> = Json.decodeFromString(value)

}