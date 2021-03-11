package com.github.kazenetu.listview.domain.domain

import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface

/**
 * Todoエンティティ
 */
class TodoEntity private constructor(
    override val id: Int,
    override var showImage: Boolean,
    override var title: String,
    override var detail: String,
    override var isDone: Boolean
) :TodoItemInterface {

    companion object {
        /**
         * エンティティ生成
         */
        fun create(
            id: Int,
            showImage: Boolean,
            title: String,
            detail: String,
            isDone: Boolean
        ): TodoEntity {
            return TodoEntity(id, showImage, title, detail, isDone)
        }

        /**
         * TodoItemInterfaceからエンティティ生成
         */
        fun create(
            src:TodoItemInterface
        ): TodoEntity {
            return TodoEntity(src.id, src.showImage, src.title, src.detail, src.isDone)
        }
    }
}