package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem

/**
 * Doneリスト用ViewModel
 */
class DoneViewModel(repository: TodoRepository):ViewModel(repository) {

    init{
        changeddDone.observeForever({
            select()
        })
    }

    /**
     * 選択対象取得
     */
    override suspend fun getSelectData():List<TodoItem> {
        return repository.doneData()
    }
}