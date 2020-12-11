package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository

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