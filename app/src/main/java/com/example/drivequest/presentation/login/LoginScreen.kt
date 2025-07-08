package com.example.drivequest.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val loginSuccessEvent = viewModel.loginSuccessEvent
    val loginErrorEvent = viewModel.loginErrorEvent

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
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
            LoginForm(navController, viewModel)
        }
    }
}

@Composable
fun LoginForm(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val emailInputState by viewModel.emailInputState.collectAsState()
    val passwordInputState by viewModel.passwordInputState.collectAsState()
    val isLoginEnabled = viewModel.isLoginEnabled.value
    val uiState by viewModel.buttonState.collectAsState()

    FormCard(
        top = {
            UnderlineText(
                "ログイン",
                modifier = Modifier.padding(bottom = 32.dp),
                textColor = Color.Gray,
                underlineColor = Color.Gray
            )
        },
        inputs = {
            LabeledOutlinedTextFieldWithError(
                value = emailInputState.input,
                onValueChange = { viewModel.updateEmailInput(it) },
                labelText = "メールアドレス",
                errorMessage = emailInputState.validationError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            LabeledOutlinedTextFieldWithError(
                value = passwordInputState.input,
                onValueChange = { viewModel.updatePasswordInput(it) },
                labelText = "パスワード",
                errorMessage = passwordInputState.validationError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        },
        button = {
            when (uiState) {
                is LoginViewModel.ButtonState.Loading -> CircularProgressIndicator(color = Color(0xFF4A90E2))
                is LoginViewModel.ButtonState.Idle -> ActionButton(
                    text = "ログイン",
                    enabled = isLoginEnabled,
                    onClick = { viewModel.onLoginClicked() },
                    contentPadding = PaddingValues(horizontal = 56.dp, vertical = 12.dp)
                )
            }
        },
        footerContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "新規登録はこちら",
                    modifier = Modifier.clickable { navController.navigate("registration") },
                    color = MainOrange,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "パスワードを忘れた方",
                    modifier = Modifier.clickable {},
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
            LoginScreen(navController)
        }
    }
}
