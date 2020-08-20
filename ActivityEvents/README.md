# Activityイベント確認
Activityのイベント発生パターンの調査

## 起動時(インスタンス生成とフォーカス設定)
1. onCreate ※savedInstanceState==null
1. onStart
1. onResume
1. onPostResume

## 非フォーカス時
1. onPause
1. onStop

## フォーカス時
1. onStart
1. onResume
1. onPostResume

## スマホ回転時(インスタンス再生成)
1. onPause
1. onStop
1. onDestroy
1. onCreate ※savedInstanceState!=null
1. onStart
1. onResume
1. onPostResume
