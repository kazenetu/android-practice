package com.github.kazenetu.listview.view.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
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
     * Loadingの表示制御
     */
    private var loading:Boolean = true
    private var firstSetTime:Boolean=true
    override fun setTimeOut(ms :Long){
        if(!loading) return

        if(firstSetTime){
            super.setTimeOut(ms)
        }else{
            super.setTimeOut(0L)
        }
        firstSetTime = false;
        loading = false
    }
    fun clearTimeout(){
        loading = true
    }
}