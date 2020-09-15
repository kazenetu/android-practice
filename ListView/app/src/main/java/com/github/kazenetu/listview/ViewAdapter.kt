package com.github.kazenetu.listview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(private val list: List<RowItem>, private val listener: ItemClickListener) : RecyclerView.Adapter<ItemViewHolder>() {

    /**
     * ViewHolderを作成
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Log.d("Adapter", "onCreateViewHolder")
        val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent, false)

        return ItemViewHolder(rowView)
    }

    /**
     * データとViewHolder(リストアイテム)とのバインド
     * クリックイベントも設定
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder")
        holder.titleView.text = list[position].title
        holder.detailView.text = list[position].detail

        // タップしたとき
        holder.itemView.setOnClickListener { view->
            listener.onItemClick(view, position, list[position])
        }
    }

    /**
     * アイテム数を返す
     */
    override fun getItemCount(): Int {
        Log.d("Adapter", "getItemCount")
        return list.size
    }

    /**
     * クリックイベント用インターフェイス
     */
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int, value:RowItem)
    }
}