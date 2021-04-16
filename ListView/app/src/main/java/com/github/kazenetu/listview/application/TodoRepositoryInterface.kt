package com.github.kazenetu.listview.application

import com.github.kazenetu.listview.domain.domain.TodoItemInterface
import kotlinx.coroutines.flow.Flow

/**
 * リポジトリインターフェース
 */
interface TodoRepositoryInterface {
    suspend fun todoData(): List<TodoItemInterface>
    suspend fun doneData() :List<TodoItemInterface>

    suspend fun insert(todo: TodoItemInterface)
    suspend fun update(todo: TodoItemInterface)
    suspend fun delete(todo: TodoItemInterface)
}