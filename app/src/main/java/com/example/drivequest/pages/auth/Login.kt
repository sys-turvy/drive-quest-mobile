package com.example.drivequest.pages.auth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.MainBlue
import com.example.drivequest.ui.theme.MainOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController){
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
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LoginForm(navController)
        }
    }
}

@Composable
fun LoginForm(navController: NavController) {
    val email = remember{ mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showEmailError = remember { mutableStateOf(false) }
    val showPasswordError = remember { mutableStateOf(false) }
    val emailErrorMessage = remember { mutableStateOf("") }
    val passwordErrorMessage = remember { mutableStateOf("") }

    FormCard(
        "ログイン",
        inputs = {
            LabeledOutlinedTextFieldWithError(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                labelText = "メールアドレス",
                isError = showEmailError.value,
                errorMessage = emailErrorMessage.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            LabeledOutlinedTextFieldWithError(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                labelText = "パスワード",
                isError = showPasswordError.value,
                errorMessage = passwordErrorMessage.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        },
        button = {
            ActionButton(
                text = "ログイン",
                onClick = {},
                contentPadding = PaddingValues(
                    horizontal = 56.dp,
                    vertical = 12.dp
                )
            )
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