package com.example.drivequest.pages.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.drivequest.data.remote.api.weather.WeatherResponse

@Composable
fun WeatherInfoCard(
    weather: WeatherResponse,
    modifier: Modifier = Modifier
) {
    // 天気アイコンのURLを作成
    val iconUrl = "https://openweathermap.org/img/wn/${weather.weather.first().icon}@2x.png"

    // カードの外枠を定義
    Card(
        modifier = modifier, // 外部から modifier 指定可能
        shape = RoundedCornerShape(16.dp), // 角丸の指定
        colors = CardDefaults.cardColors(containerColor = Color.White), // カード背景色指定
        elevation = CardDefaults.cardElevation(4.dp) // 影の高さ指定
    ) {
        // 横並びのレイアウト
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), // 内側の余白指定
            verticalAlignment = Alignment.CenterVertically // 垂直中央揃え
        ) {
            // 天気アイコン画像
            Icon(
                painter = rememberAsyncImagePainter(iconUrl), // URLから画像取得
                contentDescription = weather.weather.first().description, // アクセシビリティ対応
                tint = Color.Unspecified, // 元画像の色を利用
                modifier = Modifier.size(36.dp) // アイコンサイズ指定
            )
            Spacer(Modifier.width(8.dp)) // アイコンとテキストの間にスペース追加
            // 温度・都市名の表示
            Column {
                // 気温表示（太字）
                Text(
                    text = "${weather.main.temp.toInt()}°C",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // 都市名の表示（グレー、やや小さめの文字）
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}