package com.example.drivequest.pages

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.drivequest.R
import com.example.drivequest.ui.theme.MainOrange


//　入力したフレンドIDと一致した時
@Composable
fun FriendAdditionDialog (user: MockUser,onDismiss: () -> Unit){
    BaseDialogContainer(onDismiss = onDismiss) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(160.dp)
                .width(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(top=20.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                // ユーザーアイコン
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    AsyncImage(
                        model = user.imageUrl,
                        contentDescription = "ユーザーアイコン",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.placeholder_icon),
                        error = painterResource(R.drawable.error_icon),
                        modifier = Modifier.matchParentSize()
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
                // ユーザー名
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        //フレンド追加ボタン
        Button(
            //フレンド追加処理
            onClick = {

            },
            modifier = Modifier
                .height(40.dp)
                .width(100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainOrange),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            shape = MaterialTheme.shapes.small, // 角の丸み（お好みで）

        ) {
            Text(
                "追加",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


