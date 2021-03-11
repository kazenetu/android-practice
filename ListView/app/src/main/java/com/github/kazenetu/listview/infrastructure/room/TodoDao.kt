package com.github.kazenetu.listview.infrastructure.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * データベースにアクセスする際に使用するメソッドインターフェース
 */
@Dao
interface TodoDao {
    @Query("SELECT * FROM items WHERE isDone = 0 ORDER BY id DESC")
    fun getTodo(): Flow<List<TodoItem>>

    @Query("SELECT * FROM items WHERE isDone = 1 ORDER BY id DESC")
    fun getDone(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: TodoItem)

    @Update
    suspend fun update(todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)
}