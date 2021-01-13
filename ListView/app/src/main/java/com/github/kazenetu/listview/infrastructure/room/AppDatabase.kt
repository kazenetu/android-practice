package com.github.kazenetu.listview.infrastructure.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}