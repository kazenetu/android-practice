package com.github.kazenetu.listview.application

import com.github.kazenetu.listview.domain.domain.TodoEntity
import com.github.kazenetu.listview.domain.interfaces.TodoRepositoryInterface

class TodoApplicationService(private val repository: TodoRepositoryInterface) {
    suspend fun todoData() = repository.todoData()
    suspend fun doneData() = repository.doneData()

    suspend fun insert(todo: TodoEntity) = repository.insert(todo)
    suspend fun update(todo: TodoEntity) = repository.update(todo)
    suspend fun delete(todo: TodoEntity) = repository.delete(todo)
}