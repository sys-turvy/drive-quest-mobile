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
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.MainBlue

@Composable
fun NewPasswordPage(modifier: Modifier,navController: NavController,onChangeCompleteClick:() ->Unit){
    val password = remember { mutableStateOf("") }
    val confirmpassword = remember { mutableStateOf("") }
    val showPasswordError = remember { mutableStateOf(false) }
    val showConfirmPasswordError = remember { mutableStateOf(false) }
    val confirmPasswordErrorMessage = remember { mutableStateOf("") }
    var hasError = false
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
            .height(550.dp)
            .padding(top = 70.dp)
            .fillMaxSize(),
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                "新しいパスワードを設定",
                color = FontGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 70.dp),
            )
            // 下線を自作
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(2.dp)
                    .background(FontGray)

            )
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
                        //パスワード一致 → ここで、新しいパスワードの設定処理

                        onChangeCompleteClick()
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
                    "変更",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }

}

@Preview
@Composable
fun NewPasswordPagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            NewPasswordPage(
                modifier = Modifier,
                navController = navController,
                onChangeCompleteClick = {})
        }
    }
}