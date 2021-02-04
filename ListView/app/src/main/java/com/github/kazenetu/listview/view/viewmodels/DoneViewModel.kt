package com.github.kazenetu.listview.view.viewmodels

import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface

/**
 * Doneリスト用ViewModel
 */
class DoneViewModel(applicationService: TodoApplicationService): ViewModel(applicationService) {

    init{
        changedDone.observeForever({
            select()
        })
    }

    /**
     * 選択対象取得
     */
    override suspend fun getSelectData():List<TodoEntity> {
        return applicationService.doneData()
    }
}