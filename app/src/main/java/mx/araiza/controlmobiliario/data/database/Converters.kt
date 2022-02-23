package mx.araiza.controlmobiliario.data.database

import androidx.room.TypeConverter
import mx.araiza.controlmobiliario.core.FurnitureKind
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun dateToString(value :Date) = dateFormatter.format(value)

    @TypeConverter
    fun stringToDate(value :String) = dateFormatter.parse(value)

    @TypeConverter
    fun kindToString (value : FurnitureKind) = value.name

    @TypeConverter
    fun stringTokind(value :String) = FurnitureKind.valueOf(value)

    companion object{
        var dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    }
}