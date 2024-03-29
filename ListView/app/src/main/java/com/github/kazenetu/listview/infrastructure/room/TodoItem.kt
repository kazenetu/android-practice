package com.github.kazenetu.listview.infrastructure.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.github.kazenetu.listview.domain.domain.TodoItemInterface

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
    /**
     * コンストラクタ
     */
    constructor(id: Int, title: String, detail: String) : this(id, false, title, detail, false)

    /**
     * TodoItemInterfaceを引数とするコンストラクタ
     */
    constructor(src: TodoItemInterface) : this(src.id, src.showImage, src.title, src.detail, src.isDone)
}