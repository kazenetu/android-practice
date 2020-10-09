package com.github.kazenetu.listview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.text_row_item.view.*

/**
 * ViewHolder：リストアイテムのレイアウトとコードの設定
 */
class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val deleteTarget: ImageView = itemView.delete_target
    val titleView: TextView = itemView.row_title
    val detailView: TextView = itemView.row_detail
}