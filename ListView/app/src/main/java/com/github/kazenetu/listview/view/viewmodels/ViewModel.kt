package com.github.kazenetu.listview.view.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import com.github.kazenetu.listview.view.recyclerView.RowItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * ViewModelのスーパークラス
 */
abstract class ViewModel(protected val applicationService: TodoApplicationService): AndroidViewModel(Application()) {
    private var toggleDeleteImageFlag: MutableStateFlow<Pair<Boolean, Boolean>> =
        MutableStateFlow(Pair(first = false, second = false))
    val toggleDeleteImage: StateFlow<Pair<Boolean, Boolean>> get() = toggleDeleteImageFlag

    /**
     * 公開用リストアイテム
     */
    private var _listItems: MutableStateFlow<List<TodoEntity>> = MutableStateFlow(emptyList())
    val listItems: StateFlow<List<TodoEntity>> get() = _listItems

    /**
     * リストアイテム
     */
    private val items: List<TodoEntity>
        get() {
            return listItems.value
        }

    /**
     * コンストラクタ
     */
    init {
        CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
            updateListItems()

            changedDone.collect {
                updateListItems()
            }
        }
    }

    /**
     * 選択対象取得
     */
    protected abstract suspend fun getSelectData(): List<TodoEntity>

    suspend fun updateListItems(){
        _listItems.value = getSelectData()
    }

    /**
     * 更新
     */
    fun update(position: Int, data: RowItem) =
        CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
            if (items.size <= position) {
                return@launch
            }
            if (position < 0) {
                applicationService.insert(
                    TodoEntity.create(
                        0,
                        false,
                        data.title,
                        data.detail,
                        false
                    )
                )
            } else {
                val id = items[position].id
                applicationService.update(
                    TodoEntity.create(
                        id,
                        false,
                        data.title,
                        data.detail,
                        data.isDone
                    )
                )
            }
            updateListItems()
        }

    /**
     * Doneフラグの更新
     */
    fun updateDone(position: Int, isDone: Boolean) =
        CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
            if (items.size <= position) {
                return@launch
            }

            val data = items[position]
            applicationService.update(
                TodoEntity.create(
                    data.id,
                    false,
                    data.title,
                    data.detail,
                    isDone
                )
            )

            updateListItems()

            // 処理後の状態を取得
            changedDoneEvent.emit(Unit)
        }

    /**
     * すべて削除
     */
    fun deleteAll() = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            applicationService.delete(it)
        }
        toggleDeleteImageFlag.value = Pair(first = false, second = true)
        updateListItems()
    }

    /**
     * すべての削除イメージを非表示にする
     */
    fun hideAllDeleteImage()  {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            it.showImage = false
        }
        toggleDeleteImageFlag.value = Pair(first = false, second = true)
    }

    /**
     * 削除対象イメージ表示
     */
    fun showDeleteImage(position: Int)  {
        items[position].showImage = true
        toggleDeleteImageFlag.value = Pair(first = true, second = false)
    }

    /**
     * 削除対象イメージ非表示
     */
    fun hideDeleteImage(position: Int)  {
        items[position].showImage = false
        toggleDeleteImageFlag.value = Pair(first = items[position].showImage, second = false)
    }

    companion object {
        private var changedDoneEvent = MutableSharedFlow<Unit>()
        private val changedDone: SharedFlow<Unit> get() = changedDoneEvent
    }
}
