package com.github.kazenetu.listview

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.android.ext.android.startKoin

/**
 * アプリケーションクラス
 */
class App :android.app.Application()
{
    override fun onCreate() {
        super.onCreate()

        // Koinコンテナ生成
        startKoin(this, listOf(
            this.modules
        ))
    }

    // Koinモジュール
    private val modules: Module = module {
        factory { TodoViewModel(this@App) }
    }
}