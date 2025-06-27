package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFF87CEEB))
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "プロフィール",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(16.dp),
            color = Color.White
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Column {
                        Text(
                            text = "名前",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                        Text(
                            text = "称号",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Column(modifier = Modifier.padding( end = 60.dp)) {
                        Text(
                            text = "今日の走行距離",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "12.0km",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                    Column {
                        Text(
                            text = "今日の走行時間",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "30分",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                }
                Column(modifier = Modifier.padding(top = 20.dp)) {
                    Text(
                        text = "今日の目標走行距離",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = "20km",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 10.dp,bottom = 10.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    GoalProgressBar(12, 20)
                }
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    imageVector = Icons.Default.PeopleOutline,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(text = "友達リスト",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
        ) {
            Column {
                Text(
                    text = "- 設定 -",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                ProfileSection("ニックネーム", content = {
                    Text(
                        text = "名前",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }) { }
                ProfileSection("アイコン",content = {
                    Text(
                        text = "画像",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }) { }
                ProfileSection("目標走行距離/月",content = {
                    Text(
                        text = "20km",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }) { }
                ProfileSection("アイコンフレーム",content = {
                    Text(
                        text = "画像",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }) { }
                ProfileSection("ナビ音声",content = {
                    Text(
                        text = "ずんだもん",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }) { }
                ProfileSection("称号",content = {
                    Text(
                        text = "称号",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }) { }

            }
        }
    }
}

@Composable
fun GoalProgressBar(current: Int, goal: Int) {
    val progress = (current.toFloat() / goal).coerceIn(0f, 1f)
    val percentage = (progress * 100).roundToInt()

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFFD9D9D9)),
        contentAlignment = Alignment.CenterStart
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF4A90E2), Color(0xFF87CEEB))
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$percentage%",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileSection(label: String, content: @Composable () -> Unit, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
            FilledButton(onClick = onClick) {
            }
        }
    }
}

@Composable
fun FilledButton(onClick: () -> Unit, content: () -> Unit) {
    Button(onClick = { onClick()},
        colors = ButtonDefaults.buttonColors(
        containerColor = Color.LightGray,
        contentColor = Color.Black,
    ),
    ) {
        Text("変更",
            fontWeight = FontWeight.Bold
            )
    }
}

