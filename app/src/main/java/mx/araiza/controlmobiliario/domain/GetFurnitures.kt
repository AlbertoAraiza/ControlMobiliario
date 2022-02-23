package mx.araiza.controlmobiliario.domain

import mx.araiza.controlmobiliario.data.FurnitureRepository
import mx.araiza.controlmobiliario.data.model.FurnitureModel
import javax.inject.Inject

class GetFurnitures @Inject constructor(
    private val repository :FurnitureRepository
) {
    suspend operator fun invoke() :List<FurnitureModel>? = repository.getAllFurniture()
}