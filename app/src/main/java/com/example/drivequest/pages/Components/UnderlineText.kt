package com.example.drivequest.pages.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnderlineText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    underlineColor: Color = Color.Black,
    underlineHeight: Dp = 2.dp,
    spacing: Dp = 4.dp
) {
    var textWidth by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight,
                modifier = Modifier.onSizeChanged { size ->
                    textWidth = size.width
                }
            )
            Box(
                modifier = Modifier
                    .offset(y = spacing)
                    .height(underlineHeight)
                    .width(with(LocalDensity.current) { textWidth.toDp() + 16.dp })
                    .background(underlineColor)
                    .align(Alignment.BottomStart)
            )
        }
    }
}