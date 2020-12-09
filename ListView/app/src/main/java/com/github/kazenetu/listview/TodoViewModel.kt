package com.github.kazenetu.listview

import android.app.Application
import androidx.lifecycle.*
import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(private val repository: TodoRepository):BaseViewModel(repository) {
    /**
     * コンストラクタ
     */
    init {
        setListItem(repository.todoData)
    }
}