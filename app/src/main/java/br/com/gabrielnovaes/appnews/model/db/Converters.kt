package br.com.gabrielnovaes.appnews.model.db

import androidx.room.TypeConverter
import br.com.gabrielnovaes.appnews.model.Source


class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : Source {
        return Source(name,name)
    }
}