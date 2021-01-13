package com.github.kazenetu.listview.infrastructure.room

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM items WHERE isDone = 0 ORDER BY id DESC")
    suspend fun getTodo(): List<TodoItem>

    @Query("SELECT * FROM items WHERE isDone = 1 ORDER BY id DESC")
    suspend fun getDone(): List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: TodoItem)

    @Update
    suspend fun update(todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)
}