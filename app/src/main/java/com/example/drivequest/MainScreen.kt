package com.example.drivequest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.pages.HomePage
import com.example.drivequest.pages.DriveHistoryPage
import com.example.drivequest.pages.RankingPage
import com.example.drivequest.pages.StorePage
import com.example.drivequest.pages.ProfilePage



@Composable
fun MainScreen(modifier: Modifier = Modifier) {


    val navItemList = listOf(

        NavItem("ランキング", Icons.Default.Leaderboard),
        NavItem("ストア", Icons.Filled.Store),
        NavItem("ホーム", Icons.Default.Home),
        NavItem("プロフィール", Icons.Default.Person),
        NavItem("運転履歴", Icons.Default.DirectionsCar)
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected =  selectedIndex == index ,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon,
                            contentDescription = "Icon",
                            modifier = Modifier.size(40.dp))
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                fontSize = 10.sp
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    Column(modifier = modifier.fillMaxSize()) {
        when (selectedIndex) {
            0 -> RankingPage()
            1 -> StorePage()
            2 -> HomePage()
            3 -> ProfilePage()
            4 -> DriveHistoryPage()
        }
    }
}