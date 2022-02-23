package mx.araiza.controlmobiliario.core

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object  PermissionsUtility {
    fun hasReadPermission(ctx : Context) = EasyPermissions.hasPermissions(ctx, Manifest.permission.READ_EXTERNAL_STORAGE)
}