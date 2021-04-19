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
    /**
     * 公開用リストアイテム
     */
    private var _listItems: MutableStateFlow<List<TodoEntity>> = MutableStateFlow(emptyList())
    val listItems: StateFlow<List<TodoEntity>> get() = _listItems

    /**
     * リストアイテム
     */
    protected val items: List<TodoEntity>
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

    protected suspend fun updateListItems(){
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


    companion object {
        private var changedDoneEvent = MutableSharedFlow<Unit>()
        private val changedDone: SharedFlow<Unit> get() = changedDoneEvent
    }
}
