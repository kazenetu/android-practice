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

    /**
     * Loadingが表示されているか否か
     */
    private var shownLoading:Boolean = true

    /**
     * すでに実行されているか否か
     */
    private var executedSetTimeout:Boolean=false

    /**
     * 設定時間後のイベント発行
     */
    override fun setTimeOut(ms :Long){
        // Loadingが非表示の場合は終了
        if(!shownLoading) return

        if(executedSetTimeout){
            // すでに初回実行がされている場合は即座にイベント発行
            super.setTimeOut(0L)
        }else{
            // 初回実行の場合は一定時間後にイベント発行
            super.setTimeOut(ms)
        }

        // 初回実行済みに設定
        executedSetTimeout = true

        // Loadingを非表示に設定
        shownLoading = false
    }

    /**
     * Loadingを表示状態に変更
     */
    fun resetLoadingFlag(){
        shownLoading = true
    }

    var addButtonExpanded :Boolean = true
}