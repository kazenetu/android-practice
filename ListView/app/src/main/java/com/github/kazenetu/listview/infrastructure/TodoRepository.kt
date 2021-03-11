package com.github.kazenetu.listview.infrastructure

import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.domain.interfaces.TodoRepositoryInterface
import com.github.kazenetu.listview.infrastructure.room.TodoDao
import com.github.kazenetu.listview.infrastructure.room.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * TODOリポジトリ
 */
class TodoRepository(private val todoDao: TodoDao): TodoRepositoryInterface {
    override fun todoData() = todoDao.getTodo() as Flow<List<TodoItemInterface>>
    override fun doneData() = todoDao.getDone() as Flow<List<TodoItemInterface>>

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