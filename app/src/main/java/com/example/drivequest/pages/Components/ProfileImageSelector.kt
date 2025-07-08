package com.example.drivequest.pages.Components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.drivequest.ui.theme.MainBlue

@Composable
fun ProfileImageSelector(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    imageSize: Dp = 76.dp,
    onSelectImageClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // プロフィール画像またはアイコン
        Box(
            modifier = Modifier
                .padding(top = 35.dp)
                .size(imageSize)
                .background(color = Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "プロフィール画像",
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "プロフィール追加",
                    tint = Color.White,
                    modifier = Modifier.size(imageSize * 0.78f)
                )
            }
        }

        // 「アイコンの画像を選ぶ」ボタン
        TextButton(
            modifier = Modifier.padding(top = 8.dp),
            onClick = onSelectImageClick
        ) {
            Text(
                text = "アイコンの画像を選ぶ",
                color = MainBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}