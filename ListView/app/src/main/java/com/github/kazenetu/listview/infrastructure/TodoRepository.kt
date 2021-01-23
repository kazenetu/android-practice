package com.github.kazenetu.listview.infrastructure

import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.infrastructure.room.TodoDao
import com.github.kazenetu.listview.infrastructure.room.TodoItem

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun todoData() = todoDao.getTodo() as List<TodoItemInterface>
    suspend fun doneData() = todoDao.getDone() as List<TodoItemInterface>

    suspend fun insert(todo: TodoItemInterface) {
        todoDao.insert(todo as TodoItem)
    }

    suspend fun update(todo: TodoItemInterface) {
        todoDao.update(todo as TodoItem)
    }

    suspend fun delete(todo: TodoItemInterface) {
        todoDao.delete(todo as TodoItem)
    }
}