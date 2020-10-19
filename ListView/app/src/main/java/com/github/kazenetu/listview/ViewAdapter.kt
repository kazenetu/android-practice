package com.github.kazenetu.listview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.room.TodoItem

class ViewAdapter internal constructor(
    context: Context,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var list = emptyList<TodoItem>()

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

        if(list[position].showImage){
            holder.deleteTarget.visibility = View.VISIBLE
        } else {
            holder.deleteTarget.visibility = View.GONE
        }

        // タップしたとき
        holder.itemView.setOnClickListener { view->
            listener.onItemClick(view, position, list[position])
        }
        holder.itemView.setOnLongClickListener{view->
            listener.onItemLongClickListener(view, position, list[position])
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
     * 表示用コレクションを設定
     */
    internal fun setList(items: List<TodoItem>) {
        this.list = items
        notifyDataSetChanged()
    }

    /**
     * クリックイベント用インターフェイス
     */
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int, value:TodoItem)
        fun onItemLongClickListener(view: View, position: Int, value:TodoItem):Boolean
    }
}