package com.example.drivequest.pages.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    mileage: Int,
    onMileageSelected: (Int) -> Unit,
    options: List<Int> = listOf(10, 20, 30, 40, 50, 100, 200)
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = if (mileage == 0) "" else "$mileage km"
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {}, // 入力禁止（ドロップダウン専用）
            readOnly = true,
            label = {
                Text(
                    text = "目標走行距離:",
                    fontSize = 16.sp
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                 // レイアウトに応じて調整
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text("$option km") },
                    onClick = {
                        onMileageSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}