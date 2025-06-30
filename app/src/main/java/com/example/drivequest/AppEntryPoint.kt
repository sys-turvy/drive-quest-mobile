package com.example.drivequest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.drivequest.pages.auth.AuthenticationCodePage
import com.example.drivequest.pages.auth.ForgetPasswordPage
import com.example.drivequest.pages.auth.LoginPage
import com.example.drivequest.pages.auth.NewPasswordPage
import com.example.drivequest.pages.auth.PasswordChangeCompletePage
import com.example.drivequest.pages.auth.ProfileSetupPage
import com.example.drivequest.pages.auth.Registration
import com.example.drivequest.pages.auth.RegistrationCompletePage

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination ="auth_graph"
    ) {
        navigation(startDestination = "login", route = "auth_graph") {
            composable("login") {
                LoginPage(navController)
            }
            composable("registration") {
                Registration(navController = navController)
            }
            composable(
                "profileSetupPage/{email}/{password}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType },
                    navArgument("password") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val password = backStackEntry.arguments?.getString("password") ?: ""
                ProfileSetupPage(
                    navController = navController,
                    email = email,
                    password = password,
                )
            }
            composable("registrationcomplete")
            {
                RegistrationCompletePage(
                    navController = navController,
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
        composable("main_graph") {
            MainPage()
        }
    }
}