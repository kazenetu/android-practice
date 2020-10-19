package com.github.kazenetu.listview

import android.app.Application
import androidx.lifecycle.*
import com.github.kazenetu.listview.repository.TodoRepository
import com.github.kazenetu.listview.room.AppDatabase
import com.github.kazenetu.listview.room.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository


    private var itemIndex: MutableLiveData<Int> = MutableLiveData()
    private var deleteIndex: MutableLiveData<Int> = MutableLiveData()
    private var taggleDeleteImageFlag: MutableLiveData<Boolean> = MutableLiveData()
    val update : LiveData<Int> get() = itemIndex
    val delete: LiveData<Int> get() = deleteIndex
    val taggleDeleteImage: LiveData<Boolean> get() = taggleDeleteImageFlag

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
        val todoDao = AppDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        listItems = repository.allData
    }

    /**
     * 更新
     */
    fun update(position:Int,data:RowItem) = viewModelScope.launch(Dispatchers.IO) {
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
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach(){
            repository.delete(it)
        }

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