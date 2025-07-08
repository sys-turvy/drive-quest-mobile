package com.example.drivequest.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.Components.BottomBannerAdWithDummy // ★ここを追加！
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.google.android.gms.ads.AdSize

@Composable
fun DriveHistoryPage(modifier: Modifier = Modifier) {
    val logs = listOf(
        DriveLog("6/18", "9:20", "10:20", 60, 70.5),
        DriveLog("6/17", "8:00", "9:15", 40, 23.5),
        DriveLog("6/16", "13:20", "14:05", 30, 12.3),
    )

    // バナーの高さを取得
    val context = LocalContext.current
    val density = LocalDensity.current
    val displayMetrics = context.resources.displayMetrics
    val adWidthPixels = displayMetrics.widthPixels
    val adWidthDp = (adWidthPixels / displayMetrics.density).toInt()
    val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidthDp)
    val bannerDpHeight = with(density) { adSize.getHeightInPixels(context).toDp() }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bannerDpHeight), // バナー分のスペースを空ける
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "運転履歴",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
            DriveLogList(logs)
        }

        // ここで共通コンポーネントを呼び出し
        BottomBannerAdWithDummy(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DriveLogList(logs: List<DriveLog>) {
    LazyColumn {
        items(logs) { log ->
            DriveLogItem(log)
        }
    }
}

@Composable
fun DriveLogItem(log: DriveLog) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Row {
                    Text(
                        text = log.date,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                Text(
                    text = "${log.startTime}〜${log.endTime}",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(120.dp),
                    color = Color(0xFF4A90E2),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = buildAnnotatedString {
                        append("走行距離: ")
                        withStyle(style = SpanStyle(color = Color(0xFFDC143C))) {
                            append("${log.distance} km")
                        }
                    }
                )
                Text(
                    text = buildAnnotatedString {
                        append("運転時間: ")
                        withStyle(style = SpanStyle(color = Color(0xFFFF7F50))) {
                            append("${log.durationTime} 分")
                        }
                    }
                )
            }
        }
    }
}

data class DriveLog(
    val date: String,
    val startTime: String,
    val endTime: String,
    val durationTime: Int,
    val distance: Double
)

@Preview
@Composable
fun DriveHistoryPagePreview() {
    DriveQuestTheme {
        GradientBackground {
            DriveHistoryPage()
        }
    }
}
