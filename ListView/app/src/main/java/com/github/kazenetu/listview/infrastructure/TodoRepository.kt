package com.github.kazenetu.listview.infrastructure

import com.github.kazenetu.listview.domain.domain.TodoItemInterface
import com.github.kazenetu.listview.application.TodoRepositoryInterface
import com.github.kazenetu.listview.infrastructure.room.TodoDao
import com.github.kazenetu.listview.infrastructure.room.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * TODOリポジトリ
 */
class TodoRepository(private val todoDao: TodoDao): TodoRepositoryInterface {
    override suspend fun todoData() = todoDao.getTodo()
    override suspend fun doneData() = todoDao.getDone()

    /**
     * 追加
     */
    override suspend fun insert(todo: TodoItemInterface) {
        todoDao.insert(TodoItem(todo))
    }

    /**
     * 更新
     */
    override suspend fun update(todo: TodoItemInterface) {
        todoDao.update(TodoItem(todo))
    }

    /**
     * 削除
     */
    override suspend fun delete(todo: TodoItemInterface) {
        todoDao.delete(TodoItem(todo))
    }
}