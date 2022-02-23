package mx.araiza.controlmobiliario.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mx.araiza.controlmobiliario.data.database.dao.FurnitureDAO
import mx.araiza.controlmobiliario.data.database.entities.FurnitureEntity

@Database(entities = [FurnitureEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FurnitureDB :RoomDatabase() {
    abstract fun getFurnitureDAO() : FurnitureDAO
}