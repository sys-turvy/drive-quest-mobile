package com.example.drivequest.presentation.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.pages.components.GradientBackground
import com.example.drivequest.pages.components.BottomBannerAdWithDummy
import com.example.drivequest.ui.theme.DriveQuestTheme
import androidx.compose.material.icons.filled.Share
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.drivequest.presentation.ranking.model.RankingUiModel
import com.google.android.gms.ads.AdSize
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val weeklyRanking by viewModel.weeklyRanking.collectAsState()
    val weeklyMyRanking by viewModel.weeklyMyRanking.collectAsState()
    val monthlyRanking by viewModel.monthlyRanking.collectAsState()
    val monthlyMyRanking by viewModel.monthlyMyRanking.collectAsState()
    val error by viewModel.error.collectAsState()

    val selectedRanking = if (selectedTabIndex == 0) monthlyRanking else weeklyRanking
    val me = if (selectedTabIndex == 0) monthlyMyRanking else weeklyMyRanking

    LaunchedEffect(Unit) {
        viewModel.loadWeeklyRanking()
        viewModel.loadMonthlyRanking()
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "ランキング",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RankingScreen(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        selectedTabIndex = selectedTabIndex,
                        onTabChange = { selectedTabIndex = it },
                        monthlyRanking = monthlyRanking,
                        weeklyRanking = weeklyRanking
                    )
                    if (me != null) MyRank(me = me) else Text("自分自身のランキングが取得できませんでした")
                }
                Spacer(modifier= Modifier.size(12.dp))
                BottomBannerAdWithDummy(
                    modifier = Modifier.fillMaxWidth().zIndex(3f)
                )
            }
        }
    }
}

@Composable
fun MyRank(me: RankingUiModel) {
    val myRankBackgroundColor = when (me.rank) {
        1 -> Color(0xFFFFEA74)
        2 -> Color(0xFFBEBEBE)
        3 -> Color(0xFFA77C3F)
        else -> Color(0xFFE9E9E9)
    }
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "My ランク",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            // 共有処理
                            val shareText = "私は現在、${me.rank}位で、${me.distanceKm}km走行しました！" +
                                    "ドライブクエストで一緒に競い合おう！"
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            val chooser = Intent.createChooser(intent, "共有")
                            context.startActivity(chooser)
                        }
                ) {
                    Text(
                        text = "共有",
                        color = Color(0xFF4A90E2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "共有",
                        tint = Color(0xFF4A90E2),
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 4.dp)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = myRankBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${me.rank}位",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(48.dp)
                    )

                    AsyncImage(
                        model = me.iconUrl ?: "https://drive-quest-s3.s3.ap-northeast-1.amazonaws.com/voice_icon/voice_zundamon.png",
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = me.name,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        fontSize = 18.sp
                    )

                    Text(
                        text = "${me.distanceKm} km",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Ranking(rankingItems: List<RankingUiModel>) {
    LazyColumn {
        itemsIndexed(rankingItems) { index, (rank, name, iconRes, score) ->
            val backgroundColor = when (rank) {
                1 -> Color(0xFFFFEA74)
                2 -> Color(0xFFBEBEBE)
                3 -> Color(0xFFA77C3F)
                else -> Color(0xFFE9E9E9)
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = backgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${rank}位",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(48.dp)
                    )

                    AsyncImage(
                        model = iconRes ?: "https://drive-quest-s3.s3.ap-northeast-1.amazonaws.com/voice_icon/voice_zundamon.png",
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        fontSize = 18.sp
                    )

                    Text(
                        text = "$score km",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabChange: (Int) -> Unit,
    monthlyRanking: List<RankingUiModel>,
    weeklyRanking: List<RankingUiModel>
) {
    val tabs = listOf("月別", "週別")
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = index == selectedTabIndex
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) Color(0xFF4A90E2) else Color.White)
                            .clickable { onTabChange(index) }
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Color.White else Color(0xFF4A90E2),
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            val rankingItems = if (selectedTabIndex == 0) monthlyRanking else weeklyRanking
            Ranking(rankingItems = rankingItems)
        }
    }
}

@Preview
@Composable
fun RankingPagePreview() {
    DriveQuestTheme {
        GradientBackground {
            RankingScreen(modifier = Modifier)
        }
    }
}
