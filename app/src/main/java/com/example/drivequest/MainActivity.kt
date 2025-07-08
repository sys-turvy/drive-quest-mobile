package com.example.drivequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)  // 広告表示の初期化
        enableEdgeToEdge()
        setContent {
            DriveQuestTheme {
                GradientBackground {
                    AppEntryPoint()
                }
            }
        }
    }
}