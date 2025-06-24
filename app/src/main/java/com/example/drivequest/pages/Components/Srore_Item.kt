package com.example.drivequest.pages.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun FrameCard(
    imageUrl: String = "",
    frameName: String = "??フレーム",
    price: Int = 200,
    purchase: Int = 0,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {} // 追加
) {
    val corner = 16.dp
    val defaultImageUrl = "https://play-lh.googleusercontent.com/2HAZLGMx7WmmnCT5b7CAKazuEhHtTfnnCPDrAI9FY3gYsGXfvpxby0j0qj3PSixc4w"
    val cardBgColor = if (purchase == 1) Color(0xFF616161) else Color.White

    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(corner))
            .clip(RoundedCornerShape(corner))
            .background(cardBgColor)
            .padding(16.dp)
            .clickable(enabled = purchase == 0) { onClick() }, // 購入済みはタップ無効
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = if (imageUrl.isNotBlank()) imageUrl else defaultImageUrl,
                contentDescription = frameName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = frameName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = if (purchase == 1) Color.White else Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${price}円",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (purchase == 1) Color.White else Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (purchase == 1) {
            Text(
                text = "購入済み",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color(0x99000000), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}