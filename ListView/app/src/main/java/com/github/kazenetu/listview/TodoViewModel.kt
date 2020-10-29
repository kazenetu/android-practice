package com.github.kazenetu.listview

import android.app.Application
import androidx.lifecycle.*
import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(private val repository: TodoRepository):AndroidViewModel(Application()) {

    private var itemIndex: MutableLiveData<Int> = MutableLiveData()
    private var deleteIndex: MutableLiveData<Int> = MutableLiveData()
    private var taggleDeleteImageFlag: MutableLiveData<Pair<Boolean,Boolean>> = MutableLiveData()
    val update : LiveData<Int> get() = itemIndex
    val delete: LiveData<Int> get() = deleteIndex
    val taggleDeleteImage: LiveData<Pair<Boolean,Boolean>> get() = taggleDeleteImageFlag

    /**
     * 公開用リストアイテム
     */
    val listItems: LiveData<List<TodoItem>>

    private val items: List<TodoItem>
        get(){
            if(listItems.value != null){
                return  listItems.value!!
            }
            return emptyList<TodoItem>()
        }

    /**
     * コンストラクタ
     */
    init{
        listItems = repository.allData
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
            val id = listItems.value!![position]?.id
            repository.update(TodoItem(id,false, data.title,data.detail,data.isDone))
        }

        itemIndex.postValue(position)
    }

    /**
     * すべて削除
     */
    fun deleteAll() = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach(){
            repository.delete(it)
        }

        deleteIndex.postValue(-1)
        taggleDeleteImageFlag.postValue(Pair(false,true))
    }

    /**
     * すべての削除イメージを非表示にする
     */
    fun hideAllDeleteImage(){
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach() {
            it.showImage = false
        }
        taggleDeleteImageFlag.postValue(Pair(false,true))
    }

    /**
     * 削除対象イメージ表示
     */
    fun showDeleteImage(position:Int){
        items[position].showImage = true
        taggleDeleteImageFlag.postValue(Pair(true,false))
    }

    /**
     * 削除対象イメージ非表示
     */
    fun hideDeleteImage(position:Int){
        items[position].showImage = false

        taggleDeleteImageFlag.postValue(Pair(items.any{it.showImage},false))
    }
}