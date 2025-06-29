package com.example.drivequest.pages.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.drivequest.pages.Components.UnderlineText

@Composable
fun FormCard(
    title: String,
    inputs: @Composable () -> Unit,
    button: @Composable () -> Unit,
    footerContent: @Composable () -> Unit = {}
) {
    Card (
        modifier = Modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnderlineText(
                title,
                modifier = Modifier
                    .padding(
                        bottom = 56.dp
                    ),
                textColor = Color.Gray,
                underlineColor = Color.Gray
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                inputs()
                Box(
                    modifier = Modifier
                        .padding(
                            top = 32.dp,
                            bottom = 16.dp
                        )
                ) {
                    button()
                }
                footerContent()
            }
        }
    }
}