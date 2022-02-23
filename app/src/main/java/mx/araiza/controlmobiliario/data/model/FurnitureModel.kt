package mx.araiza.controlmobiliario.data.model

import mx.araiza.controlmobiliario.core.FurnitureKind
import mx.araiza.controlmobiliario.data.database.entities.FurnitureEntity
import java.io.Serializable
import java.util.*

data class FurnitureModel(
    val name :String,
    val type : FurnitureKind,
    val description :String,
    val acquisitionDate : Date?,
    val photoUri :String?,
    val registerDate :Date) :Serializable

fun FurnitureEntity.toDomain() :FurnitureModel = FurnitureModel(name = this.name,
    type = this.type,
    description = this.description,
    acquisitionDate = this.acquisitionDate,
    photoUri = this.photoUri,
    registerDate = this.registerDate
)