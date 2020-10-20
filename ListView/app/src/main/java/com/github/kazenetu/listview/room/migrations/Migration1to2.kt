package com.github.kazenetu.listview.room.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1to2 {
    companion object {
        val migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE items ADD COLUMN isDone INTEGER NOT NULL DEFAULT ${false}")
            }
        }
    }
}