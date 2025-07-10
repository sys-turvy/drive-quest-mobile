package com.example.drivequest.presentation.store

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.drivequest.pages.Components.ProductFrameGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.Components.BottomBannerAdWithDummy
import com.example.drivequest.presentation.store.model.Product
import com.example.drivequest.presentation.store.model.StoreTab
import com.example.drivequest.ui.theme.DriveQuestTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun StoreScreen(
    storeViewModel: StoreViewModel = hiltViewModel()
) {
    val selectedTab by storeViewModel.selectedTab
    val iconFrames by storeViewModel.iconFrames.collectAsState()
    val voiceStyle by storeViewModel.voiceStyle.collectAsState()
    val voiceStyleError by storeViewModel.voiceStyleError.collectAsState() //ボイスのデータ取得時エラー処理ように値をとっている
    val iconFrameError by storeViewModel.iconFrameError.collectAsState()  //アイコンフレームのデータ取得時エラー処理ように値をとっている
    val detailTarget by storeViewModel.detailTarget.collectAsState()
    val purchaseConfirmTarget by storeViewModel.purchaseConfirmTarget.collectAsState()
    val showAdRemoveDialog by storeViewModel.showAdRemoveDialog.collectAsState()

    LaunchedEffect(Unit) {
        storeViewModel.loadIconFrames()
        storeViewModel.loadVoiceStyle()
    }

    if (detailTarget != null) {
        ProductDetailDialog(
            product = detailTarget!!,
            onClose = { storeViewModel.selectDetailTarget(null) },
            onPurchaseClick = { product ->
                storeViewModel.selectDetailTarget(null)
                storeViewModel.selectPurchaseConfirmTarget(product)
            }
        )
    }

    if (purchaseConfirmTarget != null) {
        AlertDialog(
            onDismissRequest = { storeViewModel.selectPurchaseConfirmTarget(null) },
            title = { Text("確認") },
            text = { Text("「${purchaseConfirmTarget!!.name}」を${purchaseConfirmTarget!!.price}円で本当に購入しますか？") },
            confirmButton = {
                TextButton(onClick = {
                    storeViewModel.selectPurchaseConfirmTarget(null)
                }) { Text("購入") }
            },
            dismissButton = {
                TextButton(onClick = { storeViewModel.selectPurchaseConfirmTarget(null) }) {
                    Text("キャンセル")
                }
            }
        )
    }

    if (showAdRemoveDialog) {
        AdRemoveDialog(onClose = { storeViewModel.chengeShowAdRemoveDialog(false) })
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
                        text = "ストア",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    Button(
                        onClick = {storeViewModel.chengeShowAdRemoveDialog(true)},
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.95f),
                            contentColor = Color(0xFF4A90E2)
                        ),
                        modifier = Modifier
                    ) {
                        Text(
                            "広告非表示",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            lineHeight = 16.sp
                        )
                    }

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // タブバー
                Row(
                    modifier = Modifier
                        .widthIn(max = 360.dp)
                        .background(Color.White.copy(alpha = 0.15f), shape = MaterialTheme.shapes.medium)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { storeViewModel.selectIconFrameTab() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedTab == StoreTab.IconFrame) Color.White else Color(0x332A95E2),
                            contentColor =
                                if (selectedTab == StoreTab.IconFrame) Color(0xFF4A90E2) else Color.White
                        ),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("アイコンフレーム")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { storeViewModel.selectVoiceStyleTab() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedTab == StoreTab.VoiceStyle) Color.White else Color(0x332A95E2),
                            contentColor =
                                if (selectedTab == StoreTab.VoiceStyle) Color(0xFF4A90E2) else Color.White
                        ),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("ナビ音声")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                when (selectedTab) {
                    StoreTab.IconFrame -> {
                        ProductFrameGrid(
                            products = iconFrames,
                            onProductClick = { product ->
                                storeViewModel.selectDetailTarget(product)
                            },
                            modifier = Modifier.fillMaxSize().weight(1f)
                        )
                    }
                    StoreTab.VoiceStyle -> {
                        ProductFrameGrid(
                            products = voiceStyle,
                            onProductClick = { product ->
                                storeViewModel.selectDetailTarget(product)
                            },
                            modifier = Modifier.fillMaxSize().weight(1f)
                        )
                    }
                }
                BottomBannerAdWithDummy(
                    modifier = Modifier.fillMaxWidth().zIndex(3f)
                )
            }
        }
    }
}

@Composable
fun ProductDetailDialog(
    product: Product,
    onClose: () -> Unit,
    onPurchaseClick: (Product) -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(12.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                ) {
                    AsyncImage(
                        model = product.imgUrl.ifBlank {
                            "https://play-lh.googleusercontent.com/2HAZLGMx7WmmnCT5b7CAKazuEhHtTfnnCPDrAI9FY3gYsGXfvpxby0j0qj3PSixc4w"
                        },
                        contentDescription = product.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        product.name,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "${product.price}円",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    if (product.isOwned) {
                        Text(
                            "購入済み",
                            color = Color(0xFF4A4A4A),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    } else {
                        Button(
                            onClick = { onPurchaseClick(product) },
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800),
                                contentColor = Color.White
                            )
                        ) {
                            Text("購入", fontSize = 20.sp)
                        }
                    }
                }
            }
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "閉じる",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun AdRemoveDialog(onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(18.dp))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "広告非表示（月額契約）",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A90E2)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "月額180円で広告が全て非表示になります。",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2),
                        contentColor = Color.White
                    )
                ) {
                    Text("購入")
                }
            }
        }
    }
}

@Preview
@Composable
fun StorePagePreview() {
    DriveQuestTheme {
        GradientBackground {
            StoreScreen()
        }
    }
}
