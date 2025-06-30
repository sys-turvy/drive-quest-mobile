package com.example.drivequest.pages.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.drivequest.pages.Components.ActionButton
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.auth.components.FormCard
import com.example.drivequest.ui.theme.DriveQuestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationCompletePage(navController:NavController) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "ドライブクエスト",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .padding(bottom = innerPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
            RegistrationCompleteForm(navController)
        }
    }
}

@Composable
fun RegistrationCompleteForm(navController:NavController) {
    FormCard(
        top = {
            Text(
                "登録が完了しました！",
                modifier = Modifier
                    .padding(top = 24.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        },
        inputs = {},
        button = {
            ActionButton(
                text = "ログイン画面に戻る",
                onClick = {
                    navController.navigate("login")
                },
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
            )
        },
    )
}

@Preview
@Composable
fun RegistrationCompletePagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            RegistrationCompletePage(navController = navController)
        }
    }
}