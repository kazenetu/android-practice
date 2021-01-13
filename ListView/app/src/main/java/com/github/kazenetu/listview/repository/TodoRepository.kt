package com.github.kazenetu.listview.repository

import com.github.kazenetu.listview.infrastructure.room.TodoDao
import com.github.kazenetu.listview.infrastructure.room.TodoItem

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun todoData() = todoDao.getTodo()
    suspend fun doneData() = todoDao.getDone()

    suspend fun insert(todo: TodoItem) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: TodoItem) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: TodoItem) {
        todoDao.delete(todo)
    }
}