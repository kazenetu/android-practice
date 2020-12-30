package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem

/**
 * Doneリスト用ViewModel
 */
class DoneViewModel(repository: TodoRepository):ViewModel(repository) {

    override suspend fun getSelectData():List<TodoItem> {
        return repository.doneData()
    }
}