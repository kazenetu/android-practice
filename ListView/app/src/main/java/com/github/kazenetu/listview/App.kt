package com.github.kazenetu.listview

import androidx.room.Room
import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.AppDatabase
import com.github.kazenetu.listview.room.migrations.Migration1to2
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel

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
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "todo_database.db")
                .addMigrations(Migration1to2.migration)
                .build()
        }
        factory { get<AppDatabase>().todoDao() }
        single { TodoRepository(get()) }
        viewModel { TodoViewModel(get()) }
        viewModel { DoneViewModel(get()) }
    }
}