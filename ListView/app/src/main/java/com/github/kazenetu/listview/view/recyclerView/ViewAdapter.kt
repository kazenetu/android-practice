package com.github.kazenetu.listview.view.recyclerView

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.databinding.TextRowItemBinding
import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface

class ViewAdapter internal constructor(
    private val context: Context,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var list = emptyList<TodoItemInterface>()

    /**
     * ViewHolderを作成
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Log.d("Adapter", "onCreateViewHolder")

        val inflater = LayoutInflater.from(parent.context)
        val binding = TextRowItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    /**
     * データとViewHolder(リストアイテム)とのバインド
     * クリックイベントも設定
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder")
        holder.titleView.text = list[position].title
        holder.detailView.text = list[position].detail

        var checkStatus = android.R.drawable.checkbox_off_background
        if (list[position].isDone) {
            checkStatus = android.R.drawable.checkbox_on_background
        }
        holder.doneButton.setImageResource(checkStatus)

        if (list[position].showImage) {
            holder.deleteTarget.visibility = View.VISIBLE
            holder.doneButton.visibility = View.INVISIBLE
        } else {
            holder.deleteTarget.visibility = View.GONE
            holder.doneButton.visibility = View.VISIBLE
        }

        // タップしたとき
        holder.mainLiner.setOnClickListener { view ->
            listener.onItemClick(view, position, list[position])
        }
        holder.mainLiner.setOnLongClickListener { view ->
            listener.onItemLongClickListener(view, position, list[position])
        }
        holder.doneButton.setOnClickListener { view ->
            if (!list[position].showImage) {
                listener.onItemDoneClick(view, position, list[position])
            }
        }

        if (list[position].showImage) {
            AnimationUtils.loadAnimation(context, R.anim.show_image).apply {
                holder.deleteTarget.animation = this
            }
            holder.mainLiner.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.mainLiner.setBackgroundColor(Color.WHITE)
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
    internal fun setList(items: List<TodoItemInterface>) {
        this.list = items
        notifyDataSetChanged()
    }

    /**
     * クリックイベント用インターフェイス
     */
    interface ItemClickListener {
        fun onItemDoneClick(view: View, position: Int, value: TodoItemInterface)
        fun onItemClick(view: View, position: Int, value: TodoItemInterface)
        fun onItemLongClickListener(view: View, position: Int, value: TodoItemInterface):Boolean
    }
}