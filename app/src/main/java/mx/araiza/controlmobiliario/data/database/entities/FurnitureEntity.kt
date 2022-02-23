package mx.araiza.controlmobiliario.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.araiza.controlmobiliario.core.FurnitureKind
import mx.araiza.controlmobiliario.data.model.FurnitureModel
import java.io.Serializable
import java.util.*

@Entity(tableName = "furniture_table")
data class FurnitureEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id :Int= 0,
    @ColumnInfo(name = "name") val name :String,
    @ColumnInfo(name = "type") val type : FurnitureKind,
    @ColumnInfo(name = "description") val description :String,
    @ColumnInfo(name = "acquisition_date") val acquisitionDate : Date?,
    @ColumnInfo(name = "photo_uri") val photoUri :String?,
    @ColumnInfo(name = "register_date") val registerDate :Date) :Serializable

fun FurnitureModel.toEntity() :FurnitureEntity = FurnitureEntity(
    name = this.name,
    type = this.type,
    description = this.description,
    acquisitionDate = this.acquisitionDate,
    photoUri = this.photoUri,
    registerDate = this.registerDate
)