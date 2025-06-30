package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme

@Composable
fun RankingPage(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
// TODO: 仮データ
    val weeklyRanking = listOf(
        Triple("ユーザーA", Icons.Default.Person, 100),
        Triple("ユーザーB", Icons.Default.Person, 99),
        Triple("ユーザーC", Icons.Default.Person, 20),
        Triple("ユーザーD", Icons.Default.Person, 3),
        Triple("ユーザーE", Icons.Default.Person, 1)
    )
    val monthlyRanking = listOf(
        Triple("ユーザーA", Icons.Default.Person, 500),
        Triple("ユーザーC", Icons.Default.Person, 450),
        Triple("ユーザーD", Icons.Default.Person, 400),
        Triple("ユーザーE", Icons.Default.Person, 350),
        Triple("ユーザーB", Icons.Default.Person, 300),
    )

    val selectedRanking = if (selectedTabIndex == 0) monthlyRanking else weeklyRanking
    val myName = "ユーザーB"
    val myRank = selectedRanking.indexOfFirst { it.first == myName } + 1
    val myScore = selectedRanking.find { it.first == myName }?.third ?: 0

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ランキング",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(525.dp)
                .padding(start = 20.dp, end = 20.dp,bottom = 10.dp)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                RankingScreen(
                    selectedTabIndex = selectedTabIndex,
                    onTabChange = { selectedTabIndex = it },
                    monthlyRanking = monthlyRanking,
                    weeklyRanking = weeklyRanking
                )
            }
        }

        MyRank(myRank = myRank, myName = myName, myScore = myScore)
    }
}

@Composable
fun MyRank(myRank: Int, myName: String, myScore: Int) {
    val myRankBackgroundColor = when (myRank - 1) {
        0 -> Color(0xFFFFEA74)
        1 -> Color(0xFFBEBEBE)
        2 -> Color(0xFFA77C3F)
        else -> Color(0xFFE9E9E9)
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "My ランク",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                color = myRankBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${myRank}位",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(48.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = myName,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        fontSize = 18.sp
                    )

                    Text(
                        text = "$myScore km",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Ranking(rankingItems: List<Triple<String, ImageVector, Int>>) {
    LazyColumn {
        itemsIndexed(rankingItems) { index, (name, iconRes, score) ->
            val backgroundColor = when (index) {
                0 -> Color(0xFFFFEA74)
                1 -> Color(0xFFBEBEBE)
                2 -> Color(0xFFA77C3F)
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
                        text = "${index + 1}位",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(48.dp)
                    )

                    Icon(
                        imageVector = iconRes,
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
    selectedTabIndex: Int,
    onTabChange: (Int) -> Unit,
    monthlyRanking: List<Triple<String, ImageVector, Int>>,
    weeklyRanking: List<Triple<String, ImageVector, Int>>
) {
    val tabs = listOf("月別", "週別")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
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

@Preview
@Composable
fun RankingPagePreview() {
    DriveQuestTheme {
        GradientBackground {
            RankingPage(modifier = Modifier)
        }
    }
}
