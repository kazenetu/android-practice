package com.github.kazenetu.listview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel: ViewModel() {
    companion object {
        private var instance =
            ViewModelProvider.NewInstanceFactory().create(TodoViewModel::class.java)

        fun getInstance() = instance
    }

    /**
     * リストアイテム
     */
    val items:MutableList<RowItem> = mutableListOf()

    /**
     * コンストラクタ
     */
    init{
        // ダミーデータ追加
        for (i in 0..49) {
            items.add(RowItem("タイトル$i","詳細$i"))
        }
    }
}