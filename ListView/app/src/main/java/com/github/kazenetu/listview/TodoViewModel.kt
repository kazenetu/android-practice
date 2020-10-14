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
    private var deleteIndex: MutableLiveData<Int> = MutableLiveData()
    private var taggleDeleteImageFlag: MutableLiveData<Boolean> = MutableLiveData()
    val update : LiveData<Int> get() = itemIndex
    val delete: LiveData<Int> get() = deleteIndex
    val taggleDeleteImage: LiveData<Boolean> get() = taggleDeleteImageFlag

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
            items.add(RowItem(false,"タイトル$i","詳細$i"))
        }
    }

    /**
     * 更新
     */
    fun update(position:Int,data:RowItem){
        if(items.size <= position){
            return
        }
        if(position < 0){
            items.add(0,RowItem(false, data.title,data.detail))
        } else {
            items[position].apply {
                title = data.title
                detail = data.detail
            }
        }

        itemIndex.postValue(position)
    }

    /**
     * すべて削除
     */
    fun deleteAll(){
        items.removeAll(items.filter { it.showImage })
        deleteIndex.postValue(-1)
        taggleDeleteImageFlag.postValue(false)
    }

    /**
     * 削除対象イメージ表示
     */
    fun showDeleteImage(position:Int){
        items[position].showImage = true
        taggleDeleteImageFlag.postValue(true)
    }

    /**
     * 削除対象イメージ非表示
     */
    fun hideDeleteImage(position:Int){
        items[position].showImage = false
        
        taggleDeleteImageFlag.postValue(items.any{it.showImage})
    }
}