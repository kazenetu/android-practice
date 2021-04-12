package com.github.kazenetu.listview.view.viewmodels

import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(applicationService: TodoApplicationService): ViewModel(applicationService) {

    /**
     * ローディングイメージ表示状態
     */
    private var shownLoading:Boolean = true;

    /**
     * ローディングイメージ表示状態
     */
    val displayedLoading get() = shownLoading

    fun hideLoading()
    {
        shownLoading = false
    }

    /**
     * 選択対象取得
     */
    override fun getSelectData(): Flow<List<TodoEntity>> {
        return applicationService.todoData()
    }
}