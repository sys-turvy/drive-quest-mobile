package com.example.drivequest.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.MainBlue

@Composable
fun PasswordChangeCompletePage(modifier: Modifier,onLoginPageClick:()->Unit){
    Text(
        text = "ドライブクエスト",
        color = Color.White,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 160.dp)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "パスワードの変更\nが完了しました！",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            modifier = Modifier
                .padding(top = 160.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                onLoginPageClick()
            },
            modifier = Modifier
                .padding(top = 70.dp)
                .width(240.dp)
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ){
            Text(
                "ログイン画面に戻る",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MainBlue
            )
        }
    }
}

@Preview
@Composable
fun PasswordChangeCompletePagePreview() {
    DriveQuestTheme {
        GradientBackground {
            PasswordChangeCompletePage(modifier = Modifier, onLoginPageClick = {})
        }
    }
}