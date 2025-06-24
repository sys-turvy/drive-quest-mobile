package com.example.drivequest.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.MainBlue
import com.example.drivequest.ui.theme.RegistrationLayout

@Composable
fun RegistrationCompletePage(modifier: Modifier = Modifier, navController:NavController,onLoginClick:() -> Unit)
{
    RegistrationLayout(modifier = modifier) {
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
                .height(400.dp)
                .padding(top = 70.dp)
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Text(
                    "登録が完了しました！",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = FontGray,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier
                        .padding(top = 100.dp)
                )
                //ログイン画面に戻るボタン
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .width(200.dp)
                        .height(45.dp)
                    ,
                    shape = RoundedCornerShape(24.dp), // 丸みを強調
                    colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
                ) {
                    Text(
                        "ログイン画面へ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}