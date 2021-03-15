package com.github.kazenetu.listview.domain.domain

/**
 * Todo要素のインターフェース
 * Rommアイテムとエンティティの共通インターフェース
 */
interface TodoItemInterface {
    val id: Int
    var showImage: Boolean
    var title: String
    var detail: String
    var isDone: Boolean
}