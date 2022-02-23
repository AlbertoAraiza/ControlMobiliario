package mx.araiza.controlmobiliario.domain

import mx.araiza.controlmobiliario.data.FurnitureRepository
import mx.araiza.controlmobiliario.data.model.FurnitureModel
import javax.inject.Inject

class AddFurniture @Inject constructor(
    private val repository :FurnitureRepository
) {
    suspend operator fun invoke(item :FurnitureModel){
        repository.addFurniture(item)
    }
}