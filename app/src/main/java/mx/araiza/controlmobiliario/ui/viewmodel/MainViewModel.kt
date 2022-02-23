package mx.araiza.controlmobiliario.ui.viewmodel

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.araiza.controlmobiliario.core.FileUtils
import mx.araiza.controlmobiliario.core.FurnitureKind
import mx.araiza.controlmobiliario.data.database.Converters.Companion.dateFormatter
import mx.araiza.controlmobiliario.data.model.FurnitureModel
import mx.araiza.controlmobiliario.databinding.ActivityMainBinding
import mx.araiza.controlmobiliario.domain.AddFurniture
import mx.araiza.controlmobiliario.domain.GetFurnitureTypes
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addFurnitureUseCase: AddFurniture,
    private val getFurnitureTypes: GetFurnitureTypes,
    private val fileUtils: FileUtils
) : ViewModel() {
    val typesAdapter = MutableLiveData<ArrayAdapter<FurnitureKind>>()
    val photoUri = MutableLiveData<String?>()
    val acquisitionDateString = MutableLiveData<String>()

    var datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Fecha de adquisición")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

    fun onLoad() {
        viewModelScope.launch {
            val adapter = getFurnitureTypes()
            typesAdapter.postValue(adapter)
            datePicker.addOnPositiveButtonClickListener { milis ->
                val dateString = dateFormatter.format(milis)
                acquisitionDateString.postValue(dateString)
            }
        }
    }

    fun addNewFurniture(binding: ActivityMainBinding) {
        viewModelScope.launch {
            val name = binding.etName.extractString()
            val desc = binding.etDescription.extractString()
            val acquisitionString = binding.etAdquisitionDate.extractString()
            val kindString = binding.etType.extractString()
            val photoUriString = photoUri.value
            val isSomethingEmpty =
                name.isEmpty() || desc.isEmpty() || kindString.isEmpty() || acquisitionString.isEmpty()
            if (isSomethingEmpty) {
                binding.etAdquisitionDate.isEndIconVisible = true
            }else{
                clearTextInputLayout(binding.etName)
                clearTextInputLayout(binding.etDescription)
                clearTextInputLayout(binding.etAdquisitionDate)
                clearTextInputLayout(binding.etType)

                val newFurniture = FurnitureModel(
                    name = name,
                    description = desc,
                    acquisitionDate = dateFormatter.parse(acquisitionString),
                    photoUri = photoUriString,
                    registerDate = Date(),
                    type = FurnitureKind.valueOf(kindString)
                )
                addFurnitureUseCase(newFurniture)
                photoUri.postValue(null)
            }
        }
    }

    fun getSelectedImage(data: Intent, ctx: Context) {
        viewModelScope.launch {
            val clipData: ClipData? = data.clipData

            val path = if (clipData == null) {
                data.data
            } else {
                val item = clipData.getItemAt(0)
                item.uri
            }
            val fileString = fileUtils.getPath(path!!, ctx)
            photoUri.postValue(fileString)
        }
    }

    private fun clearTextInputLayout(layout: TextInputLayout) {
        layout.error = null
        layout.editText!!.text.clear()
    }

    private fun TextInputLayout.extractString(): String {
        val value = this.editText!!.text.toString()
        if (value.isEmpty()) {
            this.error = "Favor de ingresar un valor"
            return ""
        }
        return value
    }
}

