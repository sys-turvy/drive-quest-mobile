package com.example.drivequest.pages.auth
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.Components.LabeledOutlinedTextFieldWithError
import com.example.drivequest.pages.Components.UnderlineText
import com.example.drivequest.pages.auth.components.FormCard
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.MainOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavController){
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
            RegistrationForm(navController)
        }
    }
}

@Composable
fun RegistrationForm(navController: NavController) {
    val email = remember{ mutableStateOf("") }
    val showEmailError = remember { mutableStateOf(false) }
    val emailErrorMessage = remember { mutableStateOf("") }

    val password = remember { mutableStateOf("") }
    val showPasswordError = remember { mutableStateOf(false) }
    val passwordErrorMessage = remember { mutableStateOf("") }

    val confirmationPassword = remember { mutableStateOf("") }
    val showConfirmationPasswordError = remember { mutableStateOf(false) }
    val confirmationPasswordErrorMessage = remember { mutableStateOf("") }

    FormCard(
        top = {
            UnderlineText(
                "新規登録",
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
            LabeledOutlinedTextFieldWithError(
                value = confirmationPassword.value,
                onValueChange = {
                    confirmationPassword.value = it
                },
                labelText = "パスワード確認",
                isError = showPasswordError.value || showConfirmationPasswordError.value,
                errorMessage = confirmationPasswordErrorMessage.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
            )
        },
        button = {
            ActionButton(
                text = "登録",
                onClick = {},
                contentPadding = PaddingValues(
                    horizontal = 56.dp,
                    vertical = 12.dp
                )
            )
        },
        footerContent = {
            Text(
                text = "アカウントお持ちの方はこちら",
                modifier = Modifier
                    .clickable{
                        navController.navigate("login")
                    },
                color = MainOrange,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    )
}

@Preview
@Composable
fun RegistrationPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            Registration(navController)
        }
    }
}