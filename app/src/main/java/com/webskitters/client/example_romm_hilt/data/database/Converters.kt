package com.webskitters.client.example_romm_hilt.data.database

import androidx.room.TypeConverter
import com.webskitters.client.example_romm_hilt.data.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }
}