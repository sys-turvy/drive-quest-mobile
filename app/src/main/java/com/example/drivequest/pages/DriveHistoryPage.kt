package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme

@Composable
fun DriveHistoryPage(modifier: Modifier = Modifier) {
    val logs = listOf(
        DriveLog("6/17", "8:00", "9:15", 40,23.5),
        DriveLog("6/16", "13:20", "14:05", 30,12.3),
    )
    Column(
        modifier = modifier
            .fillMaxSize(),
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
                Text(text = "${log.startTime}〜${log.endTime}",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(120.dp),
                    color = Color(0xFF4A90E2),

                )
            }
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