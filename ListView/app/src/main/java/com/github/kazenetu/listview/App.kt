package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.AppDatabase
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.android.ext.android.startKoin

/**
 * アプリケーションクラス
 */
class App :android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        // Koinコンテナ生成
        startKoin(
            this, listOf(
                this.modules
            )
        )
    }

    // Koinモジュール
    private val modules: Module = module {
        factory { TodoRepository(AppDatabase.getDatabase(this@App).todoDao()) }
        factory { TodoViewModel(this@App, get()) }
    }
}