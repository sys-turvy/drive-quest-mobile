package com.example.drivequest.pages
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.MainBlue
import com.example.drivequest.ui.theme.MainOrange
import com.example.drivequest.ui.theme.RegistrationLayout

@Composable
fun LoginPage(modifier: Modifier = Modifier, onForgotClick: () -> Unit, onRegisrationClick:() -> Unit){
    val email = remember{ mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showEmailError = remember { mutableStateOf(false) }
    val showPasswordError = remember { mutableStateOf(false) }
    val emailErrorMessage = remember { mutableStateOf("") }
    RegistrationLayout ( modifier = modifier) {
        Text(
            text = "ドライブクエスト",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 160.dp)
        )
        Surface (
            color = Color.White,
            shape = RoundedCornerShape(25.dp),
            tonalElevation = 20.dp ,
            modifier = Modifier
                .width(320.dp)
                .height(550.dp)
                .padding(top = 70.dp)
                .fillMaxSize(),
        ){
           Column (
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = modifier
                   .fillMaxSize()
           ){
               Text(
                   "ログイン",
                   color = FontGray,
                   fontSize = 24.sp,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.padding(top = 70.dp),
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
                           text = emailErrorMessage.value,
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

               Box(
                   modifier = Modifier
                       .width(250.dp)
                       .padding(top = 30.dp)
               ) {
                   // エラーメッセージ(パスワード)
                   if (showPasswordError.value) {
                       Text(
                           text = "パスワードを入力してください",
                           color = Color.Red,
                           fontSize = 10.sp,
                           modifier = Modifier
                               .align(Alignment.TopStart)
                               .offset(y = (-16).dp) // ← テキストフィールドより少し上に配置
                       )
                   }
                   // TextButton：右上に配置し、y軸で少し浮かせる
                   TextButton(
                       onClick = onForgotClick,
                       modifier = Modifier
                           .align(Alignment.TopEnd)
                           .offset(y = (-15).dp), // ← 安全に位置を上へずらす
                       contentPadding = PaddingValues(0.dp)
                   ) {
                       Text(
                           text = "パスワードを忘れた方",
                           fontSize = 14.sp,
                           color = MainBlue
                       )
                   }

                   // パスワード入力欄
                   OutlinedTextField(
                       value = password.value,
                       onValueChange = {
                           password.value = it
                           showPasswordError.value = false
                       },
                       label = {
                           Text(
                               text = "パスワード",
                               fontSize = 16.sp
                           )
                       },
                       visualTransformation = PasswordVisualTransformation(), // ← ●●●● 表示
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Password,
                           imeAction =  ImeAction.Done,
                       ),
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(top = 12.dp) // ← TextButtonとの重なり回避
                   )
               }
               //ログインボタン
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
                           }
                       }
                       if(password.value.isBlank()){
                           showPasswordError.value = true
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
                       "ログイン",
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp,
                       color = Color.White

                   )
               }
               //新規登録はこちら
               TextButton(
                   onRegisrationClick,
                   modifier = Modifier
                       .padding(top = 10.dp)
               ) {
                    Text(
                        "＞新規登録はこちら",
                        color = MainOrange,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
               }

           }
        }
    }
}