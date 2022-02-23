package mx.araiza.controlmobiliario.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.araiza.controlmobiliario.data.database.entities.FurnitureEntity

@Dao
interface FurnitureDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFurniture(furniture :FurnitureEntity)

    @Query("SELECT * FROM furniture_table")
    suspend fun getFurniture() :List<FurnitureEntity>
}