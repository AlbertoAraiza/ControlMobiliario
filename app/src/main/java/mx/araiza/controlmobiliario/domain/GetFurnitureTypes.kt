package mx.araiza.controlmobiliario.domain

import android.content.Context
import android.widget.ArrayAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import mx.araiza.controlmobiliario.core.FurnitureKind
import javax.inject.Inject

class GetFurnitureTypes @Inject constructor(@ApplicationContext private val ctx : Context) {
    operator fun invoke(): ArrayAdapter<FurnitureKind> {
        val types = FurnitureKind.values()
        return ArrayAdapter(ctx, android.R.layout.simple_list_item_1, types)
    }
}