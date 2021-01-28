package com.github.kazenetu.listview.infrastructure

import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.infrastructure.room.TodoDao
import com.github.kazenetu.listview.infrastructure.room.TodoItem

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun todoData() = todoDao.getTodo() as List<TodoItemInterface>
    suspend fun doneData() = todoDao.getDone() as List<TodoItemInterface>

    suspend fun insert(todo: TodoItemInterface) {
        todoDao.insert(TodoItem(todo))
    }

    suspend fun update(todo: TodoItemInterface) {
        todoDao.update(TodoItem(todo))
    }

    suspend fun delete(todo: TodoItemInterface) {
        todoDao.delete(TodoItem(todo))
    }
}