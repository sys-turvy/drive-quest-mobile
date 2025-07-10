package com.example.drivequest

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivequest.pages.HomePage
import com.example.drivequest.pages.ProfilePage
import com.example.drivequest.pages.RankingPage
import com.example.drivequest.presentation.store.StoreScreen
import com.example.drivequest.presentation.drivehistory.DriveHistoryScreen

@Composable
fun MainPage() {
    val childNavController = rememberNavController()
    val currentRoute = childNavController.currentBackStackEntryAsState().value?.destination?.route ?: "home"

    MainLayout(navController = childNavController, currentRoute = currentRoute) {
        NavHost(navController = childNavController, startDestination = "home") {
            composable("home") { HomePage() }
            composable("profile") { ProfilePage(navController = childNavController) }
            composable("ranking") { RankingPage() }
            composable("store") { StoreScreen() }
            composable("history") { DriveHistoryScreen() }
        }
    }
}
