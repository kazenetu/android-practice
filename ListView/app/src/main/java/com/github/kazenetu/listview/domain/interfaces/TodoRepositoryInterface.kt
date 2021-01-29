package com.github.kazenetu.listview.domain.interfaces

interface TodoRepositoryInterface {
    suspend fun todoData():List<TodoItemInterface>
    suspend fun doneData() :List<TodoItemInterface>

    suspend fun insert(todo: TodoItemInterface)
    suspend fun update(todo: TodoItemInterface)
    suspend fun delete(todo: TodoItemInterface)
}