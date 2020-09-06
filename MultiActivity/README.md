# 複数Activityの切り替え
Activityの切り替え確認

## 前提
* FirstActivityとSecondActivityの切り替えを実施
* SecondActivityはFirstActivityを親に設定する(NavigateTopが設定)

## 画面遷移方法
### 基本
* 基本的な画面遷移
* Activityの切り替えエフェクトが実施され、下から上にActivityがせり出すエフェクトとなる

**実装例**
```kotlin
// 遷移先の設定
val intent = Intent(this, SecondActivity::class.java).apply {
    //遷移先に渡す値を設定
}

// 画面遷移
startActivity(intent)
```

### エフェクトなし
* Activity切り替え時に一切のエフェクトを実施しない
* setContentView前に設定する

**実装例**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // エフェクトOFF
    overridePendingTransition(0, 0)

    setContentView(R.layout.activity_second)
}
```

### エフェクト設定
* Activity切り替え時に表示/非表示を設定する
* Style.xmlで設定できるが、今回はプログラムソースで設定する
* setContentView前に設定する
* NavigateTopでの画面遷移の場合はonSupportNavigateUpメソッドをオーバーライドする

**実装例(エフェクト設定)**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // エフェクト設定
    with(window) {
        requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        enterTransition = Explode()
        exitTransition = Explode()
    }

    setContentView(R.layout.activity_second)
}
```

**実装例(NavigateTopの矢印アイコンクリック)**
```kotlin
/**
 * 親Activityに戻るイベント
 */
override fun onSupportNavigateUp(): Boolean {
    // Backを実行
    onBackPressed()
    return true
}
```
