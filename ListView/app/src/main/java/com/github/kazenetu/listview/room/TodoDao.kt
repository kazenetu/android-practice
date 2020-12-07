package com.github.kazenetu.listview.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM items WHERE isDone = 0 ORDER BY id DESC")
    fun getTodo(): LiveData<List<TodoItem>>

    @Query("SELECT * FROM items WHERE isDone = 1 ORDER BY id DESC")
    fun getDone(): LiveData<List<TodoItem>>

    @Query("SELECT * FROM items WHERE id IN (:todoIds)")
    suspend fun loadAllByIds(todoIds: IntArray): List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: TodoItem)

    @Update
    suspend fun update(todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)
}