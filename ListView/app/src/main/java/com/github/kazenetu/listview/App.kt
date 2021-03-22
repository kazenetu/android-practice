package com.github.kazenetu.listview

import androidx.room.Room
import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.application.TodoRepositoryInterface
import com.github.kazenetu.listview.infrastructure.TodoRepository
import com.github.kazenetu.listview.infrastructure.room.AppDatabase
import com.github.kazenetu.listview.infrastructure.room.migrations.Migration1to2
import com.github.kazenetu.listview.view.viewmodels.DoneViewModel
import com.github.kazenetu.listview.view.viewmodels.TodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * アプリケーションクラス
 */
class App :android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        // Koinコンテナ生成
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }

    // Koinモジュール
    private val appModule = module {
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "todo_database.db")
                .addMigrations(Migration1to2.migration)
                .build()
        }
        factory { get<AppDatabase>().todoDao() }
        single<TodoRepositoryInterface> { TodoRepository(get()) }
        single { TodoApplicationService(get())}
        viewModel { TodoViewModel(get()) }
        viewModel { DoneViewModel(get()) }
    }
}