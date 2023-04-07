package com.example.newz.RoomDB

import androidx.room.TypeConverter
import com.example.newz.ModelClass.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}