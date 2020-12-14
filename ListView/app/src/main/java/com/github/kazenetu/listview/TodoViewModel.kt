package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(repository: TodoRepository):ViewModel(repository) {
    /**
     * コンストラクタ
     */
    init {
        setListItem(repository.todoData)
    }
}