package mx.araiza.controlmobiliario.ui.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import mx.araiza.controlmobiliario.R
import mx.araiza.controlmobiliario.core.PermissionsUtility
import mx.araiza.controlmobiliario.databinding.ActivityMainBinding
import mx.araiza.controlmobiliario.ui.viewmodel.MainViewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

private const val CHOOSE_FILE_REQUEST = 5
private const val READ_EXTERNAL_REQUEST = 10

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.onLoad()

        viewModel.typesAdapter.observe(this) {
            (binding.etType.editText as? AutoCompleteTextView)?.setAdapter(it)
        }

        viewModel.photoUri.observe(this) { uriString ->
            if (uriString == null) {
                binding.ivFurniturePhoto.setImageResource(R.drawable.ic_add_photo)
            } else {
                Glide.with(this).load(uriString).into(binding.ivFurniturePhoto)
            }
        }

        viewModel.acquisitionDateString.observe(this){dateString ->
            binding.etAdquisitionDate.editText!!.setText(dateString)
        }

        binding.etAdquisitionDate.setEndIconOnClickListener {
            viewModel.datePicker.show(supportFragmentManager, "DatePicker")
        }

        binding.ivFurniturePhoto.setOnClickListener {
            requestReadPermission()
        }

        binding.btnAceptar.setOnClickListener {
            viewModel.addNewFurniture(binding)
        }

        binding.etAdquisitionDate.setErrorIconOnClickListener { viewModel.datePicker.show(supportFragmentManager, "datePicker") }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CHOOSE_FILE_REQUEST && data != null)
            viewModel.getSelectedImage(data, this)
    }

    private fun getFiles() {
        val intent = Intent()
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        intent.type = "*/*"
        val extraMimeTypes = arrayOf(
            "image/jpg",
            "image/png",
            "image/jpeg"
        )
        intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        ActivityCompat.startActivityForResult(
            this,
            intent,
            CHOOSE_FILE_REQUEST,
            null
        )
    }

    private fun requestReadPermission() {
        if (PermissionsUtility.hasReadPermission(this)) {
            getFiles()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Se necesita permiso para seleccionar archivo",
                READ_EXTERNAL_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getFiles()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        }else{
            requestReadPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}