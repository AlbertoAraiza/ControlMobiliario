package mx.araiza.controlmobiliario.data

import mx.araiza.controlmobiliario.data.database.dao.FurnitureDAO
import mx.araiza.controlmobiliario.data.database.entities.toEntity
import mx.araiza.controlmobiliario.data.model.FurnitureModel
import mx.araiza.controlmobiliario.data.model.toDomain
import javax.inject.Inject

class FurnitureRepository @Inject constructor(
    private val furnitureDAO: FurnitureDAO
) {
    suspend fun getAllFurniture() :List<FurnitureModel>{
        return furnitureDAO.getFurniture().map { it.toDomain() }
    }

    suspend fun addFurniture(item :FurnitureModel){
        furnitureDAO.addFurniture(item.toEntity())
    }
}