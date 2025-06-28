package com.example.drivequest.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.drivequest.pages.Components.DropDownMenu
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.MainBlue

@Composable
fun ProfileSetupPage ( modifier: Modifier = Modifier,email: String,password:String,navController: NavController,onCompleteClick:() ->Unit){
    //選択した画像
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    //画像選択の機能に必要な変数
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri.value = uri
    }
    //ニックネーム
    val nickname = remember { mutableStateOf("") }

    val mileage = remember { mutableStateOf(0) }
    val showPicker = remember { mutableStateOf(false) }
    //テスト
//        Text(text = "登録メールアドレス: $email")
//        Text(text = "パスワード: $password")
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
            .height(600.dp)
            .padding(top = 70.dp)
            .fillMaxSize(),
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
        ){
            Text(
                "プロフィール",
                color = FontGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 40.dp),
            )
            // 下線を自作
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(2.dp)
                    .background(FontGray)
            )
            //プロフィールアイコン
            Box(
                modifier = modifier
                    .padding(top = 35.dp)
                    .size(76.dp)
                    .background(
                        color = Color.LightGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ){
                if (selectedImageUri.value != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImageUri.value),
                        contentDescription = "プロフィール画像",
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "プロフィール追加",
                        tint = Color.White,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            //プロフィールアイコン設定ボタン
            TextButton(
                modifier = Modifier
                    .padding(top = 8.dp),
                onClick = {
                    imagePickerLauncher.launch("image/*")
                }

            ) {
                Text(
                    "アイコンの画像を選ぶ",
                    color = MainBlue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            //ニックネーム
            OutlinedTextField(
                value = nickname.value,
                onValueChange = {nickname.value =it},
                modifier = Modifier
                    .padding(bottom = 25.dp),
                label = {
                    Text(
                        "ニックネーム",
                        fontSize = 16.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

            )
            //目標走行距離
            DropDownMenu(
                mileage = mileage.value,
                onMileageSelected = {mileage.value = it}
            )
            //スキップボタン
            TextButton(
                onClick = onCompleteClick,
                modifier = Modifier
                    .padding(top=10.dp)
            ) {
                Text(
                    "登録せずスキップする",
                    style = TextStyle(
                        color = MainBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                )
            }
            //登録ボタン
            Button(
                //ここにプロフィールの登録処理を書く
                onClick = onCompleteClick,
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                ,
                shape = RoundedCornerShape(24.dp), // 丸みを強調
                colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
            ) {
                Text(
                    "登録",
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
fun ProfileSetupPagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            ProfileSetupPage(
                modifier = Modifier,
                navController = navController,
                onCompleteClick = {},
                email = "123",
                password = "aa"
            )
        }
    }
}