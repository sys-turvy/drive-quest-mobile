package com.example.drivequest.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme
import com.example.drivequest.pages.Components.ActionButton
import com.example.drivequest.pages.Components.LabeledOutlinedTextFieldWithError
import com.example.drivequest.pages.Components.UnderlineText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationCodePage(navController: NavController){
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
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()},

                        ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew ,
                            contentDescription = "戻る",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .padding(bottom = innerPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
            AuthenticationForm(navController)
        }
    }
}

@Composable
fun AuthenticationForm(navController: NavController) {
    val code = remember { mutableStateOf("") }
    val codeError = remember { mutableStateOf(false) }
    val codeErrorMessage = remember { mutableStateOf("") }
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
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            UnderlineText(
                text = "認証コードを入力",
                textColor = Color.Gray,
                underlineColor = Color.Gray
            )
            Spacer(modifier = Modifier.size(2.dp))
            LabeledOutlinedTextFieldWithError(
                value = code.value,
                onValueChange = {
                    code.value = it
                },
                labelText = "認証コード",
                isError = codeError.value,
                errorMessage = codeErrorMessage.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            ActionButton(
                text = "認証",
                modifier = Modifier,
                onClick = {
                    if(codeErrorMessage.value.isBlank()){
                        codeErrorMessage.value = "認証コードを入力してください"
                        codeError.value = true
                    }else{
                        //認証処理
                    }
                },
                contentPadding = PaddingValues(
                    horizontal = 56.dp,
                    vertical = 12.dp
                )
            )
        }
    }
}

@Preview
@Composable
fun AuthenticationCodePagePreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
    }
    DriveQuestTheme {
        GradientBackground {
            AuthenticationCodePage(navController = navController)
        }
    }
}