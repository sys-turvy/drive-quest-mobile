package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.RegistrationLayout
import androidx.navigation.NavController
import com.example.drivequest.ui.theme.MainBlue

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
@Composable
fun ForgetPasswordPage(modifier: Modifier, navController:NavController, onAuthenticationCodeClick:() -> Unit ){
    RegistrationLayout(modifier = modifier) {
        val email =remember{ mutableStateOf("") }
        // エラーメッセージの内容
        val emailErrorMessage = remember { mutableStateOf("") }
        val showEmailError = remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 31.dp, top = 60.dp)
        ) {
            IconButton(
                onClick = {navController.popBackStack()},

                ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIos ,
                    contentDescription = "戻る",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Text(
            text = "ドライブクエスト",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 60.dp)
        )
        Surface (
            color = Color.White,
            shape = RoundedCornerShape(25.dp),
            tonalElevation = 20.dp ,
            modifier = Modifier
                .width(320.dp)
                .height(400.dp)
                .padding(top = 70.dp)
                .fillMaxSize(),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ){
                Text(
                    "新規パスワード設定",
                    color = FontGray,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 50.dp)
                )
                //下線
                Box(
                    modifier = Modifier
                        .width(240.dp)
                        .height(2.dp)
                        .background(FontGray)
                )
                //メールアドレス
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 30.dp)
                ){
                    // エラーメッセージ(メールアドレス)
                    if (showEmailError.value) {
                        Text(
                            text = emailErrorMessage.value,
                            color = Color.Red,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(y = (-13).dp)
                        )
                    }
                    // メールアドレス入力欄
                    OutlinedTextField(
                        value = email.value,
                        // エラーメッセージの初期化
                        onValueChange = {
                            email.value = it
                            showEmailError.value = false
                        },
                        label = {
                            Text(
                                text = "メールアドレス",
                                fontSize = 16.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .width(250.dp)
                            .padding(top = 8.dp) // エラーメッセージとの間隔
                    )
                }
                Button(
                    onClick = {
                        when {
                            email.value.isBlank() -> {
                                emailErrorMessage.value = "メールアドレスを入力してください"
                                showEmailError.value = true
                            }
                            !isValidEmail(email.value) -> {
                                emailErrorMessage.value = "有効なメールアドレスを入力してください"
                                showEmailError.value = true
                            }
                            else -> {
                                showEmailError.value = false
                                // 送信処理

                                //画面遷移
                                onAuthenticationCodeClick()
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .width(200.dp)
                        .height(45.dp)
                    ,
                    shape = RoundedCornerShape(24.dp), // 丸みを強調
                    colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
                ) {
                    Text(
                        "送信",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}