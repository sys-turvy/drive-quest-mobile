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
                            onForgotClick = {
                                navController.navigate("forgetpassword")
                            },
                            onRegisrationClick = {
                                navController.navigate("registration")
                            }
                        )
                    }
                    composable("registration") {
                        Registration(
                            navController = navController,
                            onProfileSetupClick = { email, password ->
                                val encodedEmail = Uri.encode(email)
                                val encodedPassword = Uri.encode(password)
                                navController.navigate("ProfileSetupPage/$encodedEmail/$encodedPassword")
                            }
                        )

                    }
                    composable(
                        "profileSetupPage/{email}/{password}",
                        arguments = listOf(
                            navArgument("email") { type = NavType.StringType },
                            navArgument("password") { type = NavType.StringType }
                        )
                    ) {
                        backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        val password = backStackEntry.arguments?.getString("password") ?: ""
                        ProfileSetupPage(
                            email = email, password = password,
                            navController = navController,
                            onCompleteClick = {navController.navigate("registrationcomplete")}
                        )
                    }
                    composable("registrationcomplete")
                    {
                        RegistrationCompletePage(
                            navController = navController,
                            onLoginClick = {navController.navigate("login")}
                        )
                    }
                    composable("forgetpassword")
                    {
                        ForgetPasswordPage(
                            modifier = Modifier,
                            navController = navController,
                        )
                    }
                    composable("authenticationcode") {
                        AutthenticationCodePage(
                            modifier = Modifier,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}