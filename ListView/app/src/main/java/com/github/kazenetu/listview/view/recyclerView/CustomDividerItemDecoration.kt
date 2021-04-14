package com.github.kazenetu.listview.view.recyclerView

import android.app.Activity
import android.graphics.Canvas
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(context: Activity, orientation:Int, private val listener:DrawOverListener): DividerItemDecoration( context,orientation) {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        listener.onDrawOverEvent()
    }

    interface DrawOverListener {
        fun onDrawOverEvent()
    }
}
