package com.example.drivequest.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.RegistrationLayout

@Composable

fun Registration(modifier: Modifier = Modifier, navController: NavController){
    val email = remember{ mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    RegistrationLayout (modifier =modifier){
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
                .height(550.dp)
                .padding(top = 70.dp)
                .fillMaxSize()
            ,
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
                //パスワード
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {password.value=it},
                    label = {
                        Text(
                            text="パスワード",
                            fontSize = 16.sp
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .width(250.dp)

                )

            }
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("戻る")
        }

    }
}