package com.github.kazenetu.listview.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoItem::class), version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}