package com.example.drivequest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {

    // パーミッションのコールバックを登録
    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionResults ->
            if (permissionResults.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                setupUI()
            } else {
                Toast.makeText(this, "ナビゲーションには位置情報の許可が必要です。", Toast.LENGTH_LONG).show()
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()


        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
        }
    }

    // パーミッションのチェックとリクエスト
    private fun checkAndRequestPermissions() {
        val permissionsToRequest =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            }

        val permissionsNotGranted = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsNotGranted.isEmpty()) {
            setupUI()
        } else {
            permissionsLauncher.launch(permissionsNotGranted)
        }
    }

    private fun setupUI() {
        setContent {
            DriveQuestTheme {
                MainScreen()
            }
        }
    }
}