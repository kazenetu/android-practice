package com.github.kazenetu.listview.application

import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.flow.map

/**
 * アプリケーションサービス
 */
class TodoApplicationService(private val repository: TodoRepositoryInterface) {
    suspend fun todoData() = repository.todoData().map { TodoEntity.create(it) }
    suspend fun doneData() = repository.doneData().map { TodoEntity.create(it) }

    /**
     * 登録
     */
    suspend fun insert(todo: TodoEntity) = repository.insert(todo)

    /**
     * 更新
     */
    suspend fun update(todo: TodoEntity) = repository.update(todo)

    /**
     * 削除
     */
    suspend fun delete(todo: TodoEntity) = repository.delete(todo)
}