package com.example.drivequest.pages.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledOutlinedTextFieldWithError(
    modifier: Modifier = Modifier,
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
) {
    val isError = (errorMessage != null)

    Column(
        modifier = modifier
            .width(254.dp)
    ) {
        Text(
            text = if (isError) errorMessage else " ",
            color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp, top = 16.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelText, fontSize = 16.sp) },
            isError = isError,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
        )
    }
}