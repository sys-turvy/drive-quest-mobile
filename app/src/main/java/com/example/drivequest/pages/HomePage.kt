package com.example.drivequest.pages

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.drivequest.BuildConfig
import com.example.drivequest.data.remote.api.weather.WeatherManager
import com.example.drivequest.data.remote.api.weather.WeatherResponse
import com.example.drivequest.domain.model.PlaceAPIResult
import com.example.drivequest.pages.Components.ArrivalCard
import com.example.drivequest.pages.Components.GuidanceCard
import com.example.drivequest.pages.Components.Loading
import com.example.drivequest.view_model.UiState
import com.google.android.gms.maps.GoogleMap
import com.google.android.libraries.navigation.NavigationApi
import com.google.android.libraries.navigation.NavigationView
import com.google.android.libraries.navigation.Navigator
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import com.example.drivequest.view_model.HomeViewModel
import androidx.compose.foundation.layout.navigationBarsPadding   // 追加済み
import androidx.compose.ui.tooling.preview.Preview
import com.example.drivequest.pages.Components.GradientBackground
import com.example.drivequest.ui.theme.DriveQuestTheme

@SuppressLint("MissingPermission")
@Composable
fun HomePage(
    homeViewModel: HomeViewModel = viewModel(),
) {
    val uiState             by homeViewModel.uiState.collectAsState()
    val guidanceInfo        by homeViewModel.guidanceInfo.collectAsState()
    val autocompleteResults by homeViewModel.autocompleteResults.collectAsState()
    val showAchievementsCard by homeViewModel.showAchievementsCard.collectAsState()

    var isSearchActive  by remember { mutableStateOf(false) }
    var searchText      by remember { mutableStateOf("") }
    val focusRequester  = remember { FocusRequester() }
    var isNavigatorReady by remember { mutableStateOf(false) }

    val context        = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val placesClient   = remember { Places.createClient(context) }
    val navigationView = remember { NavigationView(context).apply { keepScreenOn = true } }

    /*  天気監視  */
    val weatherManager = remember {
        WeatherManager(context, BuildConfig.OPENWEATHER_API_KEY).also { it.start() }
    }
    val weather by weatherManager.weather.collectAsState()
    DisposableEffect(weatherManager) { onDispose { weatherManager.stop() } }
    /*  */

    DisposableEffect(Unit) {
        val listener = object : NavigationApi.NavigatorListener {
            override fun onNavigatorReady(navigator: Navigator) {
                homeViewModel.onReady(placesClient, navigator)
                navigationView.getMapAsync { googleMap ->
                    googleMap.isMyLocationEnabled = true
                    googleMap.followMyLocation(GoogleMap.CameraPerspective.TILTED)
                    navigationView.setRecenterButtonEnabled(false)
                    navigationView.setReportIncidentButtonEnabled(false)
                }
                isNavigatorReady = true
            }
            override fun onError(errorCode: Int) {}
        }
        NavigationApi.getNavigator(context as Activity, listener)
        onDispose {}
    }

    LaunchedEffect(searchText) {
        snapshotFlow { searchText }
            .debounce(300L)
            .distinctUntilChanged()
            .collect { query -> homeViewModel.onSearchQueryChanged(query) }
    }
    Box(Modifier.fillMaxSize()) {
        /*********** ナビエンジン準備チェック ***********/

        if (!isNavigatorReady) {
            Loading()
        } else {
            /*********** 地図描画（NavigationView） ***********/
            AndroidView(
                factory = { navigationView },
                modifier = Modifier.fillMaxSize()
            )

            /*********** UI 切り替え ***********/
            when (uiState) {
                UiState.SEARCHING -> {
                    SearchUi(
                        isSearchActive = isSearchActive,
                        searchText = searchText,
                        autocompleteResults = autocompleteResults,
                        focusRequester = focusRequester,
                        onSearchActiveChange = { isSearchActive = it },
                        onSearchTextChange = { searchText = it },
                        onPlaceSelected = { place ->
                            homeViewModel.onPlaceSelected(place)
                            isSearchActive = false
                            searchText = ""
                        },
                        onRecenterClick = {
                            navigationView.getMapAsync {
                                it.followMyLocation(GoogleMap.CameraPerspective.TILTED)
                            }
                        },
                        weather = weather // ★★ 既存コードを保持 ★★
                    )
                }

                UiState.GUIDING -> {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
// 左側：天気カード
                            weather?.let {
                                WeatherInfoCard(
                                    weather = it,
                                    modifier = Modifier // 必要ならサイズ・装飾を追加
                                )
                            }

// 右側：現在地（GPS）ボタン
                            FloatingActionButton(
                                onClick = { /* TODO: 現在地にリセンター */ },
                                containerColor = Color.White
                            ) {
                                Icon(
                                    Icons.Filled.MyLocation,
                                    contentDescription = "Recenter"
                                )
                            }
                        }

                        GuidanceCard(
                            guidanceInfo = guidanceInfo,
                            onFinishClick = { homeViewModel.onGuidanceCompleted() }
                        )
                    }
                }

                /****************** 到着後画面 ******************/
                UiState.ARRIVED -> {
                    Column(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // 称号カード（条件に応じて表示）
                        AnimatedVisibility(
                            visible = showAchievementsCard,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            AchievementsCard(achievementsText = "走行距離100kmの称号を取得しました！")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        ArrivalCard(
                            guidanceInfo = guidanceInfo,
                            onCompleteClick = { homeViewModel.onGuidanceCompleted() },
                        )
                        //TODO:到着後の処理
                    }
                }
            }
        }
    }
    
    // NavigationViewのライフサイクル管理
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            val bundle = Bundle()
            when (event) {
                Lifecycle.Event.ON_CREATE  -> navigationView.onCreate(bundle)
                Lifecycle.Event.ON_START   -> navigationView.onStart()
                Lifecycle.Event.ON_RESUME  -> navigationView.onResume()
                Lifecycle.Event.ON_PAUSE   -> navigationView.onPause()
                Lifecycle.Event.ON_STOP    -> navigationView.onStop()
                Lifecycle.Event.ON_DESTROY -> navigationView.onDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}

/* =====================================================
   検索関連 UI
   ===================================================== */
@Composable
fun SearchUi(
    isSearchActive: Boolean,
    searchText: String,
    autocompleteResults: List<PlaceAPIResult>,
    focusRequester: FocusRequester,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onPlaceSelected: (PlaceAPIResult) -> Unit,
    onRecenterClick: () -> Unit,
    weather: WeatherResponse? // ←追加
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {

        /*
         *  天気カード ＋ 現在地 FAB を横並びで表示（検索していない時だけ）
         **/
        AnimatedVisibility(                               // ★★ 変更点① ★★
            visible = !isSearchActive,
            modifier = Modifier.fillMaxWidth()            // Row 全体を横幅いっぱいに
        ) {
            Row(                                          // ★★ 変更点② ★★
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                /*――― 左側：天気カード ―――*/
                weather?.let {
                    WeatherInfoCard(weather = it)
                }

                /*――― 右側：現在地（GPS）FAB ―――*/
                FloatingActionButton(
                    onClick = onRecenterClick,
                    containerColor = Color.White
                ) {
                    Icon(Icons.Filled.MyLocation, contentDescription = "Recenter")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            val columnModifier = if (isSearchActive)
                Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.7f)
            else
                Modifier.padding(vertical = 12.dp)
            }
            Column(
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                val columnModifier = if (isSearchActive) {
                    Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.7f)
                } else {
                    Modifier.padding(vertical = 12.dp)
                }
                Column(modifier = columnModifier) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (isSearchActive) {
                            TextField(
                                value = searchText,
                                onValueChange = onSearchTextChange,
                                modifier = Modifier.weight(1f).focusRequester(focusRequester),
                                placeholder = { Text("ここで検索") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        if (searchText.isNotEmpty()) onSearchTextChange("") else onSearchActiveChange(
                                            false
                                        )
                                    }) { Icon(Icons.Default.Close, "クリア/閉じる") }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF0F0F0),
                                    unfocusedContainerColor = Color(0xFFF0F0F0),
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true
                            )
                        } else {
                            Row(
                                modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFF0F0F0))
                                    .clickable { onSearchActiveChange(true) }.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Filled.Search, "検索", tint = Color.Gray)
                                Text("ここで検索", color = Color.Gray)
                            }
                            Button(
                                onClick = { /* TODO: 練習ルート作成のロジックを実装 */ },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF4A90E2
                                    )
                                )
                            ) { Text("練習ルート\n作成", textAlign = TextAlign.Center) }
                        }
                    }
                    AnimatedVisibility(
                        visible = isSearchActive,
                        enter = expandVertically(expandFrom = Alignment.Bottom),
                        exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(300.dp).padding(top = 8.dp)
                        ) {
                            items(autocompleteResults) { result ->
                                Column(
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        onPlaceSelected(result)
                                    }.padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Text(result.primaryText, fontWeight = FontWeight.Bold)
                                    Text(
                                        result.secondaryText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


/* =====================================================
   天気表示カード
   ===================================================== */
@Composable
fun WeatherInfoCard(
    weather: WeatherResponse,
    modifier: Modifier = Modifier
) {
    // 天気アイコンのURLを作成
    val iconUrl = "https://openweathermap.org/img/wn/${weather.weather.first().icon}@2x.png"

    // カードの外枠を定義
    Card(
        modifier = modifier, // 外部から modifier 指定可能
        shape = RoundedCornerShape(16.dp), // 角丸の指定
        colors = CardDefaults.cardColors(containerColor = Color.White), // カード背景色指定
        elevation = CardDefaults.cardElevation(4.dp) // 影の高さ指定
    ) {
        // 横並びのレイアウト
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), // 内側の余白指定
            verticalAlignment = Alignment.CenterVertically // 垂直中央揃え
        ) {
            // 天気アイコン画像
            Icon(
                painter = rememberAsyncImagePainter(iconUrl), // URLから画像取得
                contentDescription = weather.weather.first().description, // アクセシビリティ対応
                tint = Color.Unspecified, // 元画像の色を利用
                modifier = Modifier.size(36.dp) // アイコンサイズ指定
            )
            Spacer(Modifier.width(8.dp)) // アイコンとテキストの間にスペース追加
            // 温度・都市名の表示
            Column {
                // 気温表示（太字）
                Text(
                    text = "${weather.main.temp.toInt()}°C",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // 都市名の表示（グレー、やや小さめの文字）
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
