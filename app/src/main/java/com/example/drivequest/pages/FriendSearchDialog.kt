package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.ui.theme.ErrorRed
import com.example.drivequest.ui.theme.FontGray
import com.example.drivequest.ui.theme.MainOrange

@Composable
fun FriendSearchDialog(
    UserId:String,
    onDismiss: () -> Unit,
    onUserFound:(MockUser) ->Unit
) {
    val errorMessage = remember { mutableStateOf("") }
    val FriendId = remember { mutableStateOf("") }
    //仮データ
    val mockUsers = listOf(
        MockUser("111111", "山田 太郎", "https://example.com/image1.jpg"),
        MockUser("222222", "佐藤 花子", "https://example.com/image2.jpg"),
        MockUser("333333", "田中 一郎", "https://example.com/image3.jpg")
    )
    BaseDialogContainer(onDismiss = onDismiss) {
        Text(
            "友達登録",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        CopyableIdRow(UserId)
        Spacer(modifier = Modifier.height(35.dp))
        Box(modifier = Modifier.height(20.dp)) {
            if (errorMessage.value.isNotBlank()) {
                Text(
                    text = errorMessage.value,
                    color = ErrorRed,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = FriendId.value,
            onValueChange = { FriendId.value = it },
            placeholder = {
                Text("友達のIDを入力", style = TextStyle(color = FontGray))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(250.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(25.dp))
        onSearchClick(
            FriendId = FriendId.value,
            errorMessage = errorMessage,
            mockUsers = mockUsers,
            onUserFound = { user ->
                onUserFound(user) // 親に通知（→ FriendAdditionDialog を開く）
            }
        )
    }
}

//仮データクラス
data class MockUser(
    val id: String,
    val name: String,
    val imageUrl: String
)
//ダイアログの共通のパーツ
@Composable
fun BaseDialogContainer(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)) // 背景半透明
            .clickable(enabled = false) {}              // 背景クリック無効
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(320.dp)
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF60A3F4), Color(0xFF6FC8FB))
                    )
                )
                .padding(24.dp)
        ) {
            // 閉じるボタン（右上）
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "閉じる",
                        tint = Color.White,
                        modifier = Modifier.size(33.dp)
                    )
                }
            }

            // 呼び出し元が差し込むUI
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                content()
            }
        }
    }
}

//テキストコピー
@Composable
fun CopyableIdRow(userId: String) {
    val clipboardManager = LocalClipboardManager.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            "あなたのID：$userId",
            color = Color.White,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = "コピー",
            tint = Color.White,
            modifier = Modifier
                .size(18.dp)
                .clickable {
                    clipboardManager.setText(AnnotatedString(userId))
                }
        )
    }
}

//検索ボタン
@Composable
fun onSearchClick(FriendId:String, errorMessage: MutableState<String>, mockUsers: List<MockUser>,
                  onUserFound: (MockUser) -> Unit){
    Button(
        onClick = {
            if (FriendId.isBlank()) {
                errorMessage.value = "IDを入力してください"
            } else {
                val matchedUser = mockUsers.find { it.id == FriendId }
                if (matchedUser != null) {
                    errorMessage.value = ""
                    onUserFound(matchedUser)

                } else {
                    errorMessage.value = "一致するユーザーが見つかりませんでした"
                }
            }
        },
        modifier = Modifier
            .width(100.dp)
            .height(40.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MainOrange)
    ) {
        Text(
            "検索",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}