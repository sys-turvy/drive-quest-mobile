package com.example.drivequest.pages.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.drivequest.view_model.GuidanceInfo


/**
 * ナビゲーション案内中に表示する情報カード
 */
@Composable
fun GuidanceCard(
    guidanceInfo: GuidanceInfo,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            // --- 上段：目的地の表示 ---
            Text(
                text = "目的地： ${guidanceInfo.destinationName}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider() // 区切り線
            Spacer(modifier = Modifier.height(12.dp))

            // --- 下段：案内情報と終了ボタン ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn(label = "到着予定", value = guidanceInfo.eta)
                InfoColumn(label = "運転時間", value = guidanceInfo.time)
                InfoColumn(label = "距離", value = guidanceInfo.distance)

                Button(
                    onClick = onFinishClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD0021B) // 赤色
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text("終了", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * ラベルと値を縦に並べて表示する小さなコンポーネント
 */
@Composable
private fun InfoColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
