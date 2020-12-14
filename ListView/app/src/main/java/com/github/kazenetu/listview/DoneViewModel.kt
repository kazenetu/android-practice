package com.github.kazenetu.listview

import com.github.kazenetu.listview.repository.TodoRepository

/**
 * Doneリスト用ViewModel
 */
class DoneViewModel(repository: TodoRepository):ViewModel(repository) {

    /**
     * コンストラクタ
     */
    init {
        setListItem(repository.doneData)
    }
}