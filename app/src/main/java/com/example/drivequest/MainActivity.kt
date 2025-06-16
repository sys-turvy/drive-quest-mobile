package com.example.drivequest


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drivequest.pages.LoginPage
import com.example.drivequest.pages.Registration
import com.example.drivequest.ui.theme.DriveQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            DriveQuestTheme {
                NavHost(
                    navController = navController,
                    startDestination ="login"
                ) {
                    composable("login") {
                        LoginPage(
                            onForgotClick = {},
                            onRegisrationClick = {
                                navController.navigate("registration")
                            }
                        )
                    }
                    composable("registration") {
                        Registration(navController = navController)
                    }
                }
            }
        }
    }
}