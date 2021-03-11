package com.github.kazenetu.listview.domain.interfaces

import kotlinx.coroutines.flow.Flow

/**
 * リポジトリインターフェース
 */
interface TodoRepositoryInterface {
    fun todoData(): Flow<List<TodoItemInterface>>
    fun doneData() :Flow<List<TodoItemInterface>>

    suspend fun insert(todo: TodoItemInterface)
    suspend fun update(todo: TodoItemInterface)
    suspend fun delete(todo: TodoItemInterface)
}