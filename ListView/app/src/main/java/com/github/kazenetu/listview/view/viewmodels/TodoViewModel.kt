package com.github.kazenetu.listview.view.viewmodels

import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.infrastructure.room.TodoItem
import com.github.kazenetu.listview.infrastructure.TodoRepository

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(repository: TodoRepository): ViewModel(repository) {

    init{
        changedDone.observeForever({
            select()
        })
    }

    /**
     * 選択対象取得
     */
    override suspend fun getSelectData():List<TodoItemInterface> {
        return repository.todoData()
    }
}