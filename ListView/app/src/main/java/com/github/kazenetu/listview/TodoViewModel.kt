package com.github.kazenetu.listview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var itemIndex: MutableLiveData<Int> = MutableLiveData()
    val update : LiveData<Int> get() = itemIndex

    /**
     * リストアイテム
     */
    private val items:MutableList<RowItem> = mutableListOf()

    /**
     * 公開用リストアイテム
     */
    val listItems:List<RowItem> = items

    /**
     * コンストラクタ
     */
    init{
        // ダミーデータ追加
        for (i in 0..49) {
            items.add(RowItem("タイトル$i","詳細$i"))
        }
    }

    /**
     * 更新
     */
    fun update(position:Int,data:RowItem){
        if(items.size <= position){
            return
        }
        var targetPosition = position
        if(position < 0){
            items.add(0,RowItem(data.title,data.detail))
            targetPosition = 0
        } else {
            items[position].apply {
                title = data.title
                detail = data.detail
            }
        }

        itemIndex.postValue(targetPosition)
    }
}