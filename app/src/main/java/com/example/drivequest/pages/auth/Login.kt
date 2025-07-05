package com.example.drivequest.pages.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.drivequest.pages.Components.ActionButton
import com.example.drivequest.pages.auth.components.FormCard
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.Components.LabeledOutlinedTextFieldWithError
import com.example.drivequest.pages.Components.UnderlineText
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.MainBlue
import com.example.drivequest.ui.theme.MainOrange
import com.example.drivequest.viewmodel.LoginPageViewModel
import androidx.compose.runtime.getValue
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController: NavController,
    loginPageViewModel: LoginPageViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val loginSuccessEvent = loginPageViewModel.loginSuccessEvent
    val loginErrorEvent = loginPageViewModel.loginErrorEvent

    LaunchedEffect(Unit) {
        loginSuccessEvent.collect {
            navController.navigate("main_graph") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        loginErrorEvent.collect { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

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
            LoginForm(navController, loginPageViewModel)
        }
    }
}

@Composable
fun LoginForm(
    navController: NavController,
    loginPageViewModel: LoginPageViewModel
) {
    val pageState by loginPageViewModel.pageState.collectAsState()

    FormCard(
        top = {
            UnderlineText(
                "ログイン",
                modifier = Modifier
                    .padding(
                        bottom = 32.dp
                    ),
                textColor = Color.Gray,
                underlineColor = Color.Gray
            )
        },
        inputs = {
            LabeledOutlinedTextFieldWithError(
                value = pageState.emailInput,
                onValueChange = {
                    loginPageViewModel.updateEmailInput(it)
                },
                labelText = "メールアドレス",
                errorMessage = pageState.emailValidationError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            LabeledOutlinedTextFieldWithError(
                value = pageState.passwordInput,
                onValueChange = {
                    loginPageViewModel.updatePasswordInput(it)
                },
                labelText = "パスワード",
                errorMessage = pageState.passwordValidationError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        },
        button = {
            if (pageState.isAuthenticating) {
                CircularProgressIndicator(color = Color(0xFF4A90E2))
            } else {
                ActionButton(
                    text = "ログイン",
                    enabled = pageState.isLoginEnabled,
                    onClick = {loginPageViewModel.onLoginClicked()},
                    contentPadding = PaddingValues(
                        horizontal = 56.dp,
                        vertical = 12.dp
                    )
                )
            }
        },
        footerContent = {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "新規登録はこちら",
                    modifier = Modifier
                        .clickable{
                            navController.navigate("registration")
                        },
                    color = MainOrange,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "パスワードを忘れた方",
                    modifier = Modifier
                        .clickable{},
                    fontSize = 13.sp,
                    color = MainBlue,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    )
}

@Preview
@Composable
fun LoginPagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            LoginPage(navController)
        }
    }
}