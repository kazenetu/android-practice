package com.github.kazenetu.listview.view.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.view.recyclerView.RowItem
import com.github.kazenetu.listview.infrastructure.TodoRepository
import com.github.kazenetu.listview.infrastructure.room.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ViewModel(protected val repository: TodoRepository): AndroidViewModel(Application()) {
    private var toggleDeleteImageFlag: MutableLiveData<Pair<Boolean, Boolean>> = MutableLiveData()
    val toggleDeleteImage: LiveData<Pair<Boolean, Boolean>> get() = toggleDeleteImageFlag

    /**
     * 公開用リストアイテム
     */
    private var _listItems: MutableLiveData<List<TodoItemInterface>> = MutableLiveData()
    val listItems: LiveData<List<TodoItemInterface>> get() =_listItems

    /**
     * リストアイテム
     */
    private val items: List<TodoItemInterface>
        get() {
            return listItems.value ?: emptyList()
        }

    /**
     * コンストラクタ
     */
    init{
        select()
    }

    /**
     * 選択
     */
    protected fun select() {
        viewModelScope.launch{
            _listItems.postValue(getSelectData())
        }
    }

    /**
     * 選択対象取得
     */
    protected abstract suspend fun getSelectData():List<TodoItemInterface>


    /**
     * 更新
     */
    fun update(position:Int,data: RowItem) = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        if(items.size <= position){
            return@launch
        }
        if(position < 0){
            repository.insert(TodoItem(0,false, data.title,data.detail,false))
        } else {
            val id = items[position].id
            repository.update(TodoItem(id,false, data.title,data.detail,data.isDone))
        }
        // 処理後の状態を取得
        select()
    }

    /**
     * Doneフラグの更新
     */
    fun updateDone(position:Int,isDone:Boolean) = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        if (items.size <= position) {
            return@launch
        }

        val data = items[position]
        repository.update(TodoItem(data.id, false, data.title, data.detail, isDone))

        // 処理後の状態を取得
        changedDoneEvent.postValue(Unit)
    }

    /**
     * すべて削除
     */
    fun deleteAll() = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            repository.delete(it)
        }
        // 処理後の状態を取得
        select()
        this@ViewModel.toggleDeleteImageFlag.postValue(Pair(first = false, second = true))
    }

    /**
     * すべての削除イメージを非表示にする
     */
    fun hideAllDeleteImage(){
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            it.showImage = false
        }
        this.toggleDeleteImageFlag.postValue(Pair(first = false, second = true))
    }

    /**
     * 削除対象イメージ表示
     */
    fun showDeleteImage(position:Int){
        items[position].showImage = true
        this.toggleDeleteImageFlag.postValue(Pair(first = true, second = false))
    }

    /**
     * 削除対象イメージ非表示
     */
    fun hideDeleteImage(position:Int){
        items[position].showImage = false
        this.toggleDeleteImageFlag.postValue(Pair(first = items[position].showImage, second = false))
    }

    companion object{
        private var changedDoneEvent: MutableLiveData<Unit> = MutableLiveData()
        val changeddDone:LiveData<Unit> get() = changedDoneEvent
    }
}
