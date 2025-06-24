package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.drivequest.data.Product
import com.example.drivequest.data.sampleProducts
import com.example.drivequest.data.sampleFrames
import com.example.drivequest.pages.Components.ProductFrameGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex

enum class StoreTab { Icon, Frame }

@Composable
fun StorePage(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(StoreTab.Frame) }
    var products by remember { mutableStateOf(sampleProducts.toMutableList()) }
    var frames by remember { mutableStateOf(sampleFrames.toMutableList()) }
    var detailTarget by remember { mutableStateOf<Product?>(null) }
    var purchaseConfirmTarget by remember { mutableStateOf<Product?>(null) }
    var showAdRemoveDialog by remember { mutableStateOf(false) } // ★追加

    // 商品詳細ポップアップ
    if (detailTarget != null) {
        ProductDetailDialog(
            product = detailTarget!!,
            onClose = { detailTarget = null },
            onPurchaseClick = { product ->
                detailTarget = null
                purchaseConfirmTarget = product
            }
        )
    }

    // 購入の最終確認ダイアログ
    if (purchaseConfirmTarget != null) {
        AlertDialog(
            onDismissRequest = { purchaseConfirmTarget = null },
            title = { Text("確認") },
            text = { Text("「${purchaseConfirmTarget!!.name}」を${purchaseConfirmTarget!!.price}円で本当に購入しますか？") },
            confirmButton = {
                TextButton(onClick = {
                    if (selectedTab == StoreTab.Frame) {
                        products = products.map {
                            if (it.name == purchaseConfirmTarget!!.name) it.copy(purchase = 1) else it
                        }.toMutableList()
                    } else {
                        frames = frames.map {
                            if (it.name == purchaseConfirmTarget!!.name) it.copy(purchase = 1) else it
                        }.toMutableList()
                    }
                    purchaseConfirmTarget = null
                }) { Text("購入") }
            },
            dismissButton = {
                TextButton(onClick = { purchaseConfirmTarget = null }) {
                    Text("キャンセル")
                }
            }
        )
    }

    // ★追加：広告非表示案内ダイアログ
    if (showAdRemoveDialog) {
        AdRemoveDialog(onClose = { showAdRemoveDialog = false })
    }

    // ★Boxラップで重ねる
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFF87CEEB))
                )
            )
    ) {
        // ★右上にボタン設置
        Button(
            onClick = { showAdRemoveDialog = true },
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.95f),
                contentColor = Color(0xFF4A90E2)
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 12.dp)
                .zIndex(2f)
        ) {
            Text(
                "広告非表示",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )
        }

        // --- ここから既存のColumn（ほぼそのまま移動でOK） ---
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "ストア",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                    onClick = { selectedTab = StoreTab.Icon },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (selectedTab == StoreTab.Icon) Color.White else Color(0x332A95E2),
                        contentColor =
                            if (selectedTab == StoreTab.Icon) Color(0xFF4A90E2) else Color.White
                    ),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("アイコンフレーム")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { selectedTab = StoreTab.Frame },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (selectedTab == StoreTab.Frame) Color.White else Color(0x332A95E2),
                        contentColor =
                            if (selectedTab == StoreTab.Frame) Color(0xFF4A90E2) else Color.White
                    ),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ナビ音声")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (selectedTab) {
                StoreTab.Icon -> {
                    ProductFrameGrid(
                        products = frames,
                        onProductClick = { product ->
                            detailTarget = product
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                StoreTab.Frame -> {
                    ProductFrameGrid(
                        products = products,
                        onProductClick = { product ->
                            detailTarget = product
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        // --- Columnここまで ---
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
            // メインコンテンツ
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
                        model = product.imageUrl.ifBlank {
                            "https://play-lh.googleusercontent.com/2HAZLGMx7WmmnCT5b7CAKazuEhHtTfnnCPDrAI9FY3gYsGXfvpxby0j0qj3PSixc4w"
                        },
                        contentDescription = product.name,
                        modifier = Modifier.size(120.dp)
                            .clip(RoundedCornerShape(16.dp)) // ここが重要！
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
                    if (product.purchase == 1) {
                        Text(
                            "購入済み",
                            //color = Color(0xFF4A4A4A), // グレー地なら濃いグレー文字もおすすめ
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
            // 閉じるボタン
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

// ★追加: 広告非表示案内用ダイアログ
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
fun StoreTabPreview(){
    StorePage()
}
