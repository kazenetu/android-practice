package com.github.kazenetu.listview.view.recyclerView

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.databinding.TextRowItemBinding

/**
 * ViewHolder：リストアイテムのレイアウトとコードの設定
 */
class ItemViewHolder(binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root){
    val deleteTarget: ImageView = binding.deleteTarget
    val mainLiner:LinearLayout =  binding.mainlinear
    val liner:LinearLayout =  binding.linear
    val titleView: TextView = binding.rowTitle
    val detailView: TextView = binding.rowDetail
    var doneButton: ImageView = binding.donekButton
}