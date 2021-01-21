package com.github.kazenetu.listview.domain.interfaces

interface TodoItemInterface {
    val id: Int
    var showImage: Boolean
    var title: String
    var detail: String
    var isDone: Boolean
}