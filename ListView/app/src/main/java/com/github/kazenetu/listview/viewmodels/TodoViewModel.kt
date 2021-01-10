package com.github.kazenetu.listview.viewmodels

import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(repository: TodoRepository): ViewModel(repository) {

    init{
        changeddDone.observeForever({
            select()
        })
    }

    /**
     * 選択対象取得
     */
    override suspend fun getSelectData():List<TodoItem> {
        return repository.todoData()
    }
}