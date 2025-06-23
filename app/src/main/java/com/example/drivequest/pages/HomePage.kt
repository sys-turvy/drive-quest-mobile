package com.example.drivequest.pages


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun HomePage(modifier: Modifier = Modifier) {
    // 1. 表示したい場所の座標を定義 (元のコードにあった大阪の座標)
    val osakaStation = LatLng(34.702485, 135.495951)

    // 2. 地図のカメラ位置を記憶・管理するためのStateを作成
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(osakaStation, 15f) // 初期位置とズームレベルを設定
    }

    // 3. GoogleMap Composableを配置
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // 4. 地図上にマーカーを配置
        Marker(
            state = MarkerState(position = osakaStation),
            title = "大阪駅",
            snippet = "ここが中心です"
        )
    }
}

