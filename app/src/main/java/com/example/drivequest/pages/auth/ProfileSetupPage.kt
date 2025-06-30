package com.example.drivequest.pages.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.drivequest.pages.Components.ActionButton
import com.example.drivequest.pages.Components.DropDownMenuWithError
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.pages.Components.LabeledOutlinedTextFieldWithError
import com.example.drivequest.pages.Components.ProfileImageSelector
import com.example.drivequest.pages.auth.components.FormCard
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.ui.theme.MainBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupPage (navController: NavController, email: String, password:String){
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "ドライブクエスト",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ProfileSetupForm(navController)
        }
    }
}

@Composable
fun ProfileSetupForm(navController: NavController) {
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri.value = uri
    }
    val nickname = remember { mutableStateOf("") }
    val mileage = remember { mutableIntStateOf(0) }
    FormCard(
        "プロフィール設定",
        inputs = {
            ProfileImageSelector(
                imageUri = selectedImageUri.value,
                onSelectImageClick = {
                    imagePickerLauncher.launch("image/*")
                }
            )
            LabeledOutlinedTextFieldWithError(
                value = nickname.value,
                onValueChange = {
                    nickname.value = it
                },
                labelText = "ニックネーム",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            DropDownMenuWithError(
                labelText = "目標月走行距離",
                value = mileage.intValue,
                showValue = "${mileage.intValue} km",
                onValueChange = {
                    mileage.intValue = it
                },
                options = listOf(0, 10, 20, 30, 40, 50, 60, 70,80,90,100,110,120,130,140,150,160,170,180,190,200,250,300,350),
            )
        },
        button = {
            ActionButton(
                text = "設定",
                onClick = {},
                contentPadding = PaddingValues(
                    horizontal = 56.dp,
                    vertical = 12.dp
                )
            )
        },
        footerContent = {
            Text(
                text = "設定をスキップする",
                modifier = Modifier
                    .clickable{},
                fontSize = 13.sp,
                color = MainBlue,
                fontWeight = FontWeight.SemiBold,
            )
        }
    )
}

@Preview
@Composable
fun ProfileSetupPagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            ProfileSetupPage(
                navController,
                email = "123",
                password = "aa"
            )
        }
    }
}