package com.github.kazenetu.listview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val repository: TodoRepository): AndroidViewModel(Application()) {
    private var itemIndex: MutableLiveData<Int> = MutableLiveData()
    private var deleteIndex: MutableLiveData<Int> = MutableLiveData()
    private var taggleDeleteImageFlag: MutableLiveData<Pair<Boolean, Boolean>> = MutableLiveData()
    val update : LiveData<Int> get() = itemIndex
    val delete: LiveData<Int> get() = deleteIndex
    val taggleDeleteImage: LiveData<Pair<Boolean, Boolean>> get() = taggleDeleteImageFlag

    /**
     * 公開用リストアイテム
     */
    lateinit var listItems: LiveData<List<TodoItem>>

    private val items: List<TodoItem>
        get(){
            if(listItems.value != null){
                return  listItems.value!!
            }
            return emptyList()
        }

    fun setListItem(items: LiveData<List<TodoItem>>) {
        viewModelScope.launch{
            listItems = items
        }
    }

    /**
     * 更新
     */
    fun update(position:Int,data:RowItem) = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        if(items.size <= position){
            return@launch
        }
        if(position < 0){
            repository.insert(TodoItem(0,false, data.title,data.detail,false))
        } else {
            val id = listItems.value!![position].id
            repository.update(TodoItem(id,false, data.title,data.detail,data.isDone))
        }

        itemIndex.postValue(position)
    }

    /**
     * すべて削除
     */
    fun deleteAll() = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            repository.delete(it)
        }

        deleteIndex.postValue(-1)
        this@BaseViewModel.taggleDeleteImageFlag.postValue(Pair(first = false, second = true))
    }

    /**
     * すべての削除イメージを非表示にする
     */
    fun hideAllDeleteImage(){
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            it.showImage = false
        }
        this.taggleDeleteImageFlag.postValue(Pair(first = false, second = true))
    }

    /**
     * 削除対象イメージ表示
     */
    fun showDeleteImage(position:Int){
        items[position].showImage = true
        this.taggleDeleteImageFlag.postValue(Pair(first = true, second = false))
    }

    /**
     * 削除対象イメージ非表示
     */
    fun hideDeleteImage(position:Int){
        items[position].showImage = false
        this.taggleDeleteImageFlag.postValue(Pair(first = items[position].showImage, second = false))
    }
}