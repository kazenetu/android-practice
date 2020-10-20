package com.github.kazenetu.listview.room

import androidx.room.*

/**
 * リストアイテム
 */
@Entity(tableName = "items")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @Ignore var showImage: Boolean,
    var title: String,
    var detail: String,
    @ColumnInfo(defaultValue = "false") var isDone: Boolean
){
    constructor(id:Int,title: String,detail: String):this(id,false,title,detail,false)
}