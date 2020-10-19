package com.github.kazenetu.listview.repository

import androidx.lifecycle.LiveData
import com.github.kazenetu.listview.room.TodoDao
import com.github.kazenetu.listview.room.TodoItem

class TodoRepository(private val todoDao: TodoDao) {
    val allData: LiveData<List<TodoItem>> = todoDao.getAll()

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