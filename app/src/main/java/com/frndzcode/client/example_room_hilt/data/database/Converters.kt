package com.frndzcode.client.example_room_hilt.data.database

import androidx.room.TypeConverter
import com.frndzcode.client.example_room_hilt.data.model.article.Source

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