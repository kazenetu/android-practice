package com.github.kazenetu.listview.infrastructure.room

import androidx.room.*
import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface

/**
 * リストアイテム
 */
@Entity(tableName = "items")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) override val id: Int,
    @Ignore override var showImage: Boolean,
    override var title: String,
    override var detail: String,
    @ColumnInfo(defaultValue = "false") override var isDone: Boolean
): TodoItemInterface {
    constructor(id: Int, title: String, detail: String) : this(id, false, title, detail, false)
}