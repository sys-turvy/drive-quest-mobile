package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import coil.compose.AsyncImage
import com.example.drivequest.pages.components.BottomBannerAdWithDummy
import com.example.drivequest.pages.components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme
import kotlin.math.roundToInt

@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavHostController) {
    var activeDialog by remember { mutableStateOf<DialogType?>(null) }
    var currentFrame by remember { mutableStateOf<Product?>(null) }
    var currentVoice by remember { mutableStateOf<Product?>(null) }
    var currentAchievement by remember { mutableStateOf<Achievement?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4A90E2), Color(0xFF87CEEB))
                    )
                )
                .padding(bottom = 75.dp), // ← バナー高さ分の下余白を追加！
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "プロフィール",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )

            // プロフィール
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                        Column {
                            Text("名前", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            Text("称号", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(modifier = Modifier.padding(top = 10.dp)) {
                        Column(modifier = Modifier.padding(end = 60.dp)) {
                            Text("今日の走行距離", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                            Text("12.0km", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("今日の走行時間", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                            Text("30分", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        Text("今日の目標走行距離", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("20km", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(vertical = 10.dp))
                    }

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        GoalProgressBar(12, 20)
                    }
                }
            }

            // 友達リストへ遷移
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(20.dp)
                    .clickable {
                        navController.navigate("friend")
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.PeopleOutline, contentDescription = null, modifier = Modifier.size(40.dp))
                    Text("友達リスト", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, modifier = Modifier.size(40.dp))
                }
            }

            // 設定
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(720.dp)
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("- 設定 -", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))
                    }

                    ProfileSection("ニックネーム", content = {
                        Text("名前", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }) {
                        activeDialog = DialogType.Nickname
                    }

                    ProfileSection("アイコン", content = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                    }) {
                        activeDialog = DialogType.Icon
                    }

                    ProfileSection("目標走行距離/月", content = {
                        Text("20km", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }) {
                        activeDialog = DialogType.Distance
                    }

                    ProfileSection("アイコンフレーム", content = {
                        Text("画像", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }) {
                        activeDialog = DialogType.Frame
                    }

                    ProfileSection("ナビ音声", content = {
                        Text("ずんだもん", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }) {
                        activeDialog = DialogType.Voice
                    }

                    ProfileSection("称号", content = {
                        Text("画像", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }) {
                        activeDialog = DialogType.Achievement
                    }
                }
            }
            Button(
                onClick = {
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF1616),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("ログアウト", fontWeight = FontWeight.Bold)
            }
        }

        // ダイアログ処理はここで
        when (activeDialog) {
            is DialogType.Nickname -> NicknameDialog { activeDialog = null }
            is DialogType.Icon -> IconDialog { activeDialog = null }
            is DialogType.Distance -> DistanceDialog { activeDialog = null }
            is DialogType.Frame -> ListDialog(
                title = "アイコンフレーム",
                frames = sampleFrames,
                selected = currentFrame,
                onClose = { activeDialog = null },
                onSave = { currentFrame = it }
            )
            DialogType.Voice -> ListDialog(
                title = "ナビボイス",
                frames = sampleFrames,
                selected = currentVoice,
                onClose = { activeDialog = null },
                onSave = { currentVoice = it }
            )
            is DialogType.Achievement -> AchievementDialog(
                title = "称号",
                achievement = sampleAchievement,
                selected = currentAchievement,
                onClose = { activeDialog = null },
                onSave = { currentAchievement = it }
            )
            null -> {}
        }

        // 画面下部にバナーを重ねて固定表示
        BottomBannerAdWithDummy(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(3f)
        )
    }
}

// 設定のテンプレ
@Composable
fun ProfileSection(label: String, content: @Composable () -> Unit, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(10.dp)) {
        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
            FilledButton(onClick = onClick)
        }
    }
}

// 変更ボタン
@Composable
fun FilledButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black,
        )
    ) {
        Text("変更", fontWeight = FontWeight.Bold)
    }
}

// 目標走行距離のバー
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

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("$percentage%", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ニックネームのダイアログ
@Composable
fun NicknameDialog(onClose: () -> Unit) {
    var nickname by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        containerColor = Color.White,
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ニックネーム",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "キャンセル",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        text = {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    label = { Text("ニックネーム") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ElevatedButton(
                    onClick = onClose,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFFF7F50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(
                        4.dp
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
                ) {
                    Text("保存", fontWeight = FontWeight.Bold)
                }
            }
        },
        modifier = Modifier.width(300.dp)
    )
}



// アイコンのダイアログ
@Composable
fun IconDialog(onClose: () -> Unit) {

    AlertDialog(
        onDismissRequest = onClose,
        containerColor = Color.White,
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "アイコン",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "キャンセル",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(96.dp)
                )
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ElevatedButton(
                    onClick = onClose,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFFF7F50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(
                        4.dp
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
                ) {
                    Text("保存", fontWeight = FontWeight.Bold)
                }
            }
        },
        modifier = Modifier.width(300.dp)
    )
}

// 目標走行距離のダイアログ
@Composable
fun DistanceDialog(onClose: () -> Unit) {
    var distance by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        containerColor = Color.White,
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "目標走行距離",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "キャンセル",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("距離") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("km", fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ElevatedButton(
                    onClick = onClose,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFFF7F50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(
                        4.dp
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
                ) {
                    Text("保存", fontWeight = FontWeight.Bold)
                }
            }
        },
        modifier = Modifier.width(300.dp)
    )
}

// フレームとボイスのダイアログ
@Composable
fun ListDialog(
    title: String,
    frames: List<Product>,
    selected: Product?,
    onClose: () -> Unit,
    onSave: (Product) -> Unit
) {
    var selectedItem by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(selected) {
        selectedItem = selected
    }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4A90E2), Color(0xFF87CEEB))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .height(600.dp)
                        .width(600.dp)
                ) {
                    items(frames) { frame ->
                        val isSelected = selectedItem?.name == frame.name
                        val defaultImageUrl = "https://play-lh.googleusercontent.com/2HAZLGMx7WmmnCT5b7CAKazuEhHtTfnnCPDrAI9FY3gYsGXfvpxby0j0qj3PSixc4w"

                        Box(
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) Color(0xFFFF7F50) else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .shadow(8.dp, RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .clickable { selectedItem = frame }
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AsyncImage(
                                        model = if (frame.imageUrl.isNotBlank()) frame.imageUrl else defaultImageUrl,
                                        contentDescription = frame.name,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = frame.name,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    ElevatedButton(
                        onClick = {
                            selectedItem?.let { onSave(it) }
                            onClose()
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFFFF7F50),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp)
                    ) {
                        Text("保存", fontWeight = FontWeight.Bold)
                    }
                }
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "キャンセル",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

// 称号のダイアログ
@Composable
fun AchievementDialog(
    title: String,
    achievement: List<Achievement>,
    selected: Achievement?,
    onClose: () -> Unit,
    onSave: (Achievement) -> Unit
) {
    var selectedItem by remember { mutableStateOf(selected) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(750.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4A90E2), Color(0xFF87CEEB))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(achievement) { achievement ->
                        val isSelected = selectedItem == achievement

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) Color(0xFFFF7F50) else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(10.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedItem = achievement },
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(Color.White)
                            ) {
                                AsyncImage(
                                    model = achievement.imageUrl,
                                    contentDescription = achievement.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ElevatedButton(
                    onClick = {
                        selectedItem?.let { onSave(it) }
                        onClose()
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFFF7F50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                ) {
                    Text("保存", fontWeight = FontWeight.Bold)
                }
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "キャンセル",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

sealed class DialogType {
    object Nickname : DialogType()
    object Icon : DialogType()
    object Distance : DialogType()
    object Frame : DialogType()
    object Voice : DialogType()
    object Achievement : DialogType()
}

data class Achievement(
    val imageUrl: String,
    val name: String
)

// テスト用
val sampleAchievement = listOf(
    Achievement(
        imageUrl = "",
        name = "テストA"
    ),
    Achievement(
        imageUrl = "",
        name = "テストB"
    ),
    Achievement(
        imageUrl = "",
        name = "テストC"
    ),
    Achievement(
        imageUrl = "",
        name = "テストD"
    ),
    Achievement(
        imageUrl = "",
        name = "テストE"
    )

)

// テスト用
data class Product(
    val purchase: Int,
    val imageUrl: String,
    val name: String,
    val price: Int
)

//テスト用
val sampleFrames = listOf(
    Product(
        purchase = 0,
        imageUrl = "",
        name = "テスト0",
        price = 360
    ),
    Product(
        purchase = 0,
        imageUrl = "",
        name = "テスト1",
        price = 360
    ),
    Product(
        purchase = 0,
        imageUrl = "",
        name = "テスト2",
        price = 360
    ),
    Product(
        purchase = 0,
        imageUrl = "",
        name = "テスト3",
        price = 360
    )
)

@Preview
@Composable
fun ProfilePagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            ProfilePage(navController = navController)
        }
    }
}