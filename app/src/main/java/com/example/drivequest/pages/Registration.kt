package com.example.drivequest.pages
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.MainBlue
import com.example.drivequest.ui.theme.MainOrange

@Composable
fun Registration(modifier: Modifier = Modifier, navController: NavController,onProfileSetupClick: (email: String, password: String) -> Unit){
    val email = remember{ mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmpassword = remember { mutableStateOf("") }
    val showEmailError = remember { mutableStateOf(false) }
    val showPasswordError = remember { mutableStateOf(false) }
    val showConfirmPasswordError = remember { mutableStateOf(false) }
    val confirmPasswordErrorMessage = remember { mutableStateOf("") }
    var hasError = false

    GradientBackground{
        Text(
            text = "ドライブクエスト",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 160.dp)
        )
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(25.dp),
            tonalElevation = 20.dp ,
            modifier = Modifier
                .width(320.dp)
                .height(600.dp)
                .padding(top = 70.dp)
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Text(
                    "新規登録",
                    color = FontGray,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 50.dp),
                )
                // 下線を自作
                Box(
                    modifier = Modifier
                        .width(120.dp)           // ← Textの幅に合わせる
                        .height(2.dp)           // ← 下線の太さ
                        .background(FontGray)   // ← 下線の色
                )
                //メールアドレス
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 30.dp)
                ) {
                    // エラーメッセージ(メールアドレス)
                    if (showEmailError.value) {
                        Text(
                            text = "メールアドレスを入力してください",
                            color = Color.Red,
                            fontSize = 10.sp,

                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(y = (-8).dp) // ← テキストフィールドより少し上に配置
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
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .width(250.dp)
                            .padding(top = 8.dp) // エラーメッセージとの間隔
                    )
                }
                //パスワード
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 20.dp)
                ){
                    // エラーメッセージ(パスワード)
                    if (showPasswordError.value) {
                        Text(
                            text = "パスワードを入力してください",
                            color = Color.Red,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(y = (-10).dp) // ← テキストフィールドより少し上に配置
                        )
                    }
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            password.value=it
                            showPasswordError.value = false
                        },
                        label = {
                            Text(
                                text="パスワード",
                                fontSize = 16.sp
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(), // ← ●●●● 表示
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .width(250.dp)
                            .padding(top = 8.dp)
                    )
                }
                //パスワード確認
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 20.dp)
                ){
                    // エラーメッセージ(パスワード)
                    if (showConfirmPasswordError.value) {
                        Text(
                            text = confirmPasswordErrorMessage.value,
                            color = Color.Red,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(y = (-10).dp) // ← テキストフィールドより少し上に配置
                        )
                    }
                    OutlinedTextField(
                        value = confirmpassword.value,
                        //エラーメッセージの初期化
                        onValueChange = {
                            confirmpassword.value=it
                            showConfirmPasswordError.value = false
                            confirmPasswordErrorMessage.value = ""
                        },
                        label = {
                            Text(
                                text="パスワード確認",
                                fontSize = 16.sp
                            )
                        },
                        // ← ●●●● 表示
                        visualTransformation = PasswordVisualTransformation(),
                        //キーボードタイプ
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction =  ImeAction.Done,
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(250.dp)
                    )
                }
                //次へボタン
                Button(
                    onClick = {
                        hasError = false
                        if(email.value.isBlank()){
                            showEmailError.value = true
                            hasError = true
                        }
                        if(password.value.isBlank()){
                            showPasswordError.value = true
                            hasError = true
                        }
                        if(confirmpassword.value.isBlank()) {
                            confirmPasswordErrorMessage.value = "パスワード確認を入力してください"
                            showConfirmPasswordError.value = true
                            hasError = true
                        } else if(password.value != confirmpassword.value) {
                            confirmPasswordErrorMessage.value = "パスワードが一致しません"
                            showConfirmPasswordError.value = true
                            hasError = true
                        } else {
                            showConfirmPasswordError.value = false
                        }
                        if (!hasError) {
                            // すべて入力され、かつパスワードも一致 → 次の処理へ
                            println("次の画面へ進みます")
                            onProfileSetupClick(email.value, password.value)
                        }
                    },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .width(200.dp)
                        .height(45.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
                ) {
                    Text(
                        "次へ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
                //アカウントお持ちの方はこちら
                TextButton(
                    onClick = {navController.popBackStack()},
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Text(
                        "＞アカウントお持ちの方はこちら",
                        color = MainOrange,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    Registration(modifier = Modifier, navController = navController, onProfileSetupClick = { name, email ->
        // ダミー処理（プレビュー用）
        println("Name: $name, Email: $email")
    })
}