package com.github.kazenetu.listview.view.viewmodels

import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Doneリスト用ViewModel
 */
class DoneViewModel(applicationService: TodoApplicationService): ViewModel(applicationService) {

    /**
     * 選択対象取得
     */
    override fun getSelectData(): Flow<List<TodoEntity>> {
        return applicationService.doneData()
    }
}