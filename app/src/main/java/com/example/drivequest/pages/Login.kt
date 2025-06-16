package com.example.drivequest.pages
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
                .fillMaxSize()
            ,
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
                   style = TextStyle(
                       textDecoration = TextDecoration.combine(
                           listOf(
                               TextDecoration.Underline,

                           ),
                       )
                   ),
               )
               //メールアドレス
               OutlinedTextField(
                   value = email.value,
                   onValueChange = {email.value=it},
                   label = {
                       Text(
                           text="メールアドレス",
                           fontSize = 16.sp
                       )
                   },
                   singleLine = true,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .width(250.dp)

               )
               Box(
                   modifier = Modifier
                       .width(250.dp)
                       .padding(top = 30.dp)
               ) {
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
                       onValueChange = { password.value = it },
                       label = {
                           Text(
                               text = "パスワード",
                               fontSize = 16.sp
                           )
                       },
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(top = 12.dp) // ← TextButtonとの重なり回避
                   )
               }
               //ログインボタン
               Button(
                   onClick = {

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