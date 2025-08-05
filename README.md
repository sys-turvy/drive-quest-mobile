# 環境構築

## APIキーの作成

・https://console.cloud.google.com/ にアクセスし、「APIとサービス」の「認証情報」にてAPIキーを作成 (Google Map API)   
  -> 作成したAPIキーの制限をなしにする
  
・https://openweathermap.org/にアクセスし、APIキーを作成 (OpenWeatherMap)


## 環境変数の設定
・secrets.propertiesをprojectレベルのappと同じ階層に作成し、以下を設定する  

```
GOOGLE_MAPS_API_KEY= "YOUR API KEY"
```

・local.propertiesに、以下を設定する  
```
ADMOB_BANNER_ID= "ca-app-pub-3940256099942544/6300978111"
OPENWEATHER_API_KEY= "YOUR API KEY"

```
