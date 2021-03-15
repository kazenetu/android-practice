package com.github.kazenetu.listview.application

import com.github.kazenetu.listview.domain.domain.TodoEntity
import kotlinx.coroutines.flow.map

/**
 * アプリケーションサービス
 */
class TodoApplicationService(private val repository: TodoRepositoryInterface) {
    fun todoData() = repository.todoData().map {it.map {item-> TodoEntity.create(item) } }
    fun doneData() = repository.doneData().map{it.map {item-> TodoEntity.create(item) } }

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