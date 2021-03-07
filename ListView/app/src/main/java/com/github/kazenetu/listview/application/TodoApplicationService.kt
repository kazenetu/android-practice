package com.github.kazenetu.listview.application

import com.github.kazenetu.listview.domain.domain.TodoEntity
import com.github.kazenetu.listview.domain.interfaces.TodoRepositoryInterface
import kotlinx.coroutines.flow.map

class TodoApplicationService(private val repository: TodoRepositoryInterface) {
    fun todoData() = repository.todoData().map {it.map {item-> TodoEntity.create(item) } }
    fun doneData() = repository.doneData().map{it.map {item-> TodoEntity.create(item) } }

    suspend fun insert(todo: TodoEntity) = repository.insert(todo)
    suspend fun update(todo: TodoEntity) = repository.update(todo)
    suspend fun delete(todo: TodoEntity) = repository.delete(todo)
}