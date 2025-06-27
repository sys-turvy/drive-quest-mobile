package com.example.drivequest.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivequest.R
import coil.compose.AsyncImage
import com.example.drivequest.pages.Components.GradientBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListPage() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val friends = List(10) { index ->
        Friend(
            id = index,
            name = "友達 ${index + 1}",
            iconUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Gull_portrait_ca_usa.jpg/300px-Gull_portrait_ca_usa.jpg",
            title = "ブロンズドライバー"
        )
    }
    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                FriendListTopAppBar(
                    onBackClick = {},
                    onAddFriendClick = {},
                    scrollBehavior =scrollBehavior
                )
            }
        ) { paddingValues ->
            FriendList(
                friends = friends,
                onFriendClick = {},
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListTopAppBar(
    onBackClick: () -> Unit,
    onAddFriendClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Text(
                "友達一覧",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    tint = Color.White,
                    contentDescription = "戻るボタン"
                )
            }
        },
        actions = {
            AddFriendOutlinedButton(onClick = onAddFriendClick)
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun AddFriendOutlinedButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF7F50) // 背景色を指定
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.small, // 角の丸み（お好みで）
    ) {
        Icon(
            imageVector = Icons.Default.PersonAdd,
            contentDescription = "友達追加",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "友達登録",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

data class Friend(
    val id: Int,
    val name: String,
    val iconUrl: String,
    val title : String
)

@Composable
fun FriendList(
    friends: List<Friend>,
    onFriendClick: (Friend) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(friends) { friend ->
            FriendListItem(friend = friend, onClick = { onFriendClick(friend) })
        }
    }
}

@Composable
fun FriendListItem(
    friend: Friend,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val color = Color(0xFFBBBBBB)

                // 上線
                drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
                // 下線
                drawLine(
                    color = color,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .background(
                color = Color
                    .White
                    .copy(alpha = 0.3f)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment
            .CenterVertically
    ) {
        UserIcon(iconUrl = friend.iconUrl)
        Spacer(modifier = Modifier.width(18.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = friend.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            TitleItem(title = friend.title, color = Color(0xFFBF911A))
        }
    }
}

@Composable
fun UserIcon(
    iconUrl: String
){
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    ) {
        AsyncImage(
            model = iconUrl,
            contentDescription = "自分のアイコン画像",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder_icon),
            error = painterResource(R.drawable.error_icon),
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun TitleItem(
    title: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 2.dp )
    ){
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun FriendListPagePreview() {
    FriendListPage()
}
