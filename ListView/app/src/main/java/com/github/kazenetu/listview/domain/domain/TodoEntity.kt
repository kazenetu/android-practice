package com.github.kazenetu.listview.domain.domain

import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface

class TodoEntity private constructor(
    override val id: Int,
    override var showImage: Boolean,
    override var title: String,
    override var detail: String,
    override var isDone: Boolean
) :TodoItemInterface {

    companion object {
        fun create(
            id: Int,
            showImage: Boolean,
            title: String,
            detail: String,
            isDone: Boolean
        ): TodoItemInterface {
            return TodoEntity(id, showImage, title, detail, isDone)
        }
    }
}