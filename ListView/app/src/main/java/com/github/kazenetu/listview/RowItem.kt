package com.github.kazenetu.listview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * リストアイテム
 */
@Parcelize
data class RowItem(var showImage:Boolean, var title:String, var detail:String,var isDone:Boolean):Parcelable