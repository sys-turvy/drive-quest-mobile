package com.example.drivequest.pages.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import coil.compose.AsyncImage // 画像読み込み用のライブラリ（Coil）をインポート

/**
 * フレームカードを表示するComposable関数。
 *
 * @param imageUrl 画像URL。空文字の場合はダミーのボックスが表示される
 * @param frameName カードに表示するフレーム名
 * @param price 表示価格
 * @param modifier 外部から適用できるModifier
 */
@Composable
fun FrameCard(
    imageUrl: String = "",
    frameName: String = "??フレーム",
    price: Int = 0,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    // カード全体のボックス
    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp)) // 影をつける
            .clip(RoundedCornerShape(16.dp))         // 角を丸く切り抜く
            .background(Color.White)                 // 背景色（白）
            .padding(8.dp)                          // 内側の余白
            .width(120.dp),                          // カードの幅
        contentAlignment = Alignment.Center          // 子要素を中央揃え
    ) {
        // カード内のレイアウト（縦方向）
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // 横方向は中央揃え
            verticalArrangement = Arrangement.Center,           // 縦も中央
            modifier = Modifier.fillMaxWidth()
        ) {
            // 画像URLが空でない場合は画像を表示
            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = imageUrl, // Coilで画像をロード
                    contentDescription = frameName,
                    modifier = Modifier
                        .size(80.dp)               // 画像サイズ
                        .clip(RoundedCornerShape(8.dp)), // 角丸
                    // contentScale = ContentScale.Crop // 必要なら出す（切り抜きモード）
                )
            } else {
                // 画像URLが空の場合、グレー色のダミーボックスを表示
                Box(
                    modifier = Modifier
                        .size(80.dp) // サイズ
                        .background(Color(0xFFB0BEC5), RoundedCornerShape(8.dp)) // グレー+角丸
                )
            }

            Spacer(modifier = Modifier.height(12.dp)) // 画像とタイトルの間隔

            // フレーム名を表示
            Text(
                text = frameName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp)) // タイトルと値段の間隔

            // 価格を表示（太字・少し大きな文字）
            Text(
                text = "${price}円",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
