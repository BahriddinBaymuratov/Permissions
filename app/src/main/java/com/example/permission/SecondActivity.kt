package com.example.permission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class SecondActivity : AppCompatActivity(), PermissionListener {
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        button = findViewById(R.id.button)

        Dexter.withContext(this)
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(this)
            .check()

    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Permission")
            .setMessage("You should allow this permission to call your friends")
            .setPositiveButton("Ok") { di, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    100
                )
                di.dismiss()
            }
            .create().show()
    }

    override fun onPermissionRationaleShouldBeShown(
        p0: PermissionRequest?,
        permission: PermissionToken?
    ) {
        permission?.continuePermissionRequest()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "You should allow permission", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", packageName, null)
            startActivity(intent)
        }
    }
}