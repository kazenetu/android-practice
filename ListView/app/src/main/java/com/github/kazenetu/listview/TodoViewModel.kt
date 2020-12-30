package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(repository: TodoRepository):ViewModel(repository) {

    override suspend fun getSelectData():List<TodoItem> {
        return repository.todoData()
    }
}