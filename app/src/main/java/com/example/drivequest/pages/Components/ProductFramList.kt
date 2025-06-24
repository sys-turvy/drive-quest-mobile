package com.example.drivequest.pages.Components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.drivequest.data.Product

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductFrameGrid(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),             // 3列
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),   // 行間余白
        horizontalArrangement = Arrangement.spacedBy(16.dp), // 列間余白
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products) { product ->
            FrameCard(
                imageUrl = product.imageUrl,
                frameName = product.name,
                price = product.price,
                purchase = product.purchase,   // ← カンマを忘れずに
                modifier = Modifier, // ※width指定は不要
                onClick = { onProductClick(product) }  // ← = を一つだけ、カンマもOK！
            )
        }
    }
}
