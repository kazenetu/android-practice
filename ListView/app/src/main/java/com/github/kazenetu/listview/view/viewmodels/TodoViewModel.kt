package com.github.kazenetu.listview.view.viewmodels

import androidx.lifecycle.viewModelScope
import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(applicationService: TodoApplicationService): ViewModel(applicationService) {

    init{
        viewModelScope.launch{
            changedDone.collect{
                select()
            }
        }
    }

    /**
     * 選択対象取得
     */
    override suspend fun getSelectData():List<TodoEntity> {
        return applicationService.todoData()
    }

    var addButtonExpanded :Boolean = true
}