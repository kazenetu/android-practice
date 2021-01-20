package com.github.kazenetu.listview.view.recyclerView

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * リストアイテム
 */
@Parcelize
data class RowItem(var showImage:Boolean, var title:String, var detail:String,var isDone:Boolean):Parcelable