package com.github.kazenetu.listview.view.viewmodels

import com.github.kazenetu.listview.application.TodoApplicationService
import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * TODOリスト用ViewModel
 */
class TodoViewModel(applicationService: TodoApplicationService): ViewModel(applicationService) {

    private var toggleDeleteImageFlag: MutableSharedFlow<Pair<Boolean, Boolean>> =
        MutableStateFlow(Pair(first = false, second = false))
    val toggleDeleteImage: SharedFlow<Pair<Boolean, Boolean>> get() = toggleDeleteImageFlag

    /**
     * すべて削除
     */
    fun deleteAll() = CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO) {
        val deleteTarget = items.filter { it.showImage }
        deleteTarget.forEach {
            applicationService.delete(it)
        }
        toggleDeleteImageFlag.emit(Pair(first = false, second = true))
        updateListItems()
    }

    /**
     * すべての削除イメージを非表示にする
     */
    fun hideAllDeleteImage() {
        runBlocking {
            val deleteTarget = items.filter { it.showImage }
            deleteTarget.forEach {
                it.showImage = false
            }
            toggleDeleteImageFlag.emit(Pair(first = false, second = true))
        }
    }

    /**
     * 削除対象イメージ表示
     */
    fun showDeleteImage(position: Int)  {
        runBlocking {
            items[position].showImage = true
            toggleDeleteImageFlag.emit(Pair(first = true, second = false))
        }
    }

    /**
     * 削除対象イメージ非表示
     */
    fun hideDeleteImage(position: Int)  {
        runBlocking {
            items[position].showImage = false
            toggleDeleteImageFlag.emit(Pair(first = items.filter { it.showImage }.isNotEmpty(), second = false))
        }
    }

    /**
     * ローディングイメージ表示状態
     */
    private var shownLoading:Boolean = true

    /**
     * ローディングイメージ表示状態
     */
    val displayedLoading get() = shownLoading

    fun hideLoading()
    {
        shownLoading = false
    }

    /**
     * 選択対象取得
     */
    override suspend fun getSelectData(): List<TodoEntity> {
        return applicationService.todoData()
    }
}