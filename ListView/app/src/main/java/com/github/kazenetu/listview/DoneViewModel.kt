package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository

/**
 * TODOリスト用ViewModel
 */
class DoneViewModel(repository: TodoRepository):BaseViewModel(repository) {

    /**
     * コンストラクタ
     */
    init {
        setListItem(repository.doneData)
    }
}