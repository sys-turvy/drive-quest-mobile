package com.example.drivequest
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drivequest.pages.AuthenticationCodePage
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.ForgetPasswordPage
import com.example.drivequest.pages.auth.LoginPage
import com.example.drivequest.pages.NewPasswordPage
import com.example.drivequest.pages.PasswordChangeCompletePage
import com.example.drivequest.pages.ProfileSetupPage
import com.example.drivequest.pages.auth.Registration
import com.example.drivequest.pages.RegistrationCompletePage
import com.example.drivequest.ui.theme.DriveQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            DriveQuestTheme {
                GradientBackground {
                    NavHost(
                        navController = navController,
                        startDestination ="login"
                    ) {
                        composable("login") {
                            LoginPage(navController)
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
                                onAuthenticationCodeClick = {navController.navigate("authenticationcode")}
                            )
                        }
                        composable("authenticationcode") {
                            AuthenticationCodePage(
                                navController = navController
                            )
                        }
                        composable("newpassword") {
                            NewPasswordPage(
                                modifier = Modifier,
                                navController = navController,
                                onChangeCompleteClick = {navController.navigate("passwordchengecomplete")}
                            )
                        }
                        composable("passwordchengecomplete") {
                            PasswordChangeCompletePage(
                                modifier = Modifier,
                                onLoginPageClick = {navController.navigate("login")}
                            )
                        }
                    }
                }
            }
        }
    }
}