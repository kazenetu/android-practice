package com.github.kazenetu.listview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.*
import com.github.kazenetu.listview.room.TodoItem
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 * Use the [DoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoneFragment : Fragment() {

    /**
     * リストビューのインスタンス
     */
    private lateinit var recyclerView: RecyclerView

    /**
     * TodoViewModelのインスタンス
     */
    private val doneViewModel: DoneViewModel by inject()

    /**
     * RecyclerView.Adapterのインスタンス
     */
    private lateinit var adapter: ViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModelの更新監視
        doneViewModel.listItems.observe(this, Observer {
            it.let{adapter.setList(it)}
        })
        doneViewModel.update.observe(this, Observer { index ->
            adapter.notifyItemChanged(index)
        })
        doneViewModel.delete.observe(this, Observer { index ->
            adapter.notifyItemRemoved(index)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_done, container, false)

        recyclerView = view.findViewById(R.id.recycler_list)

        // リストセット
        adapter = ViewAdapter(activity?.applicationContext!!, object:ViewAdapter.ItemClickListener {
            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value: TodoItem) {
            }

            /**
             * アイテム長押し
             */
            override fun onItemLongClickListener(view: View, position: Int, value: TodoItem): Boolean {
                return true
            }

            /**
             * Doneボタン
             */
            override fun onItemDoneClick(view: View, position: Int, value: TodoItem) {
                doneViewModel.update(position,
                    RowItem(false,value.title,value.detail,!value.isDone)
                )
                adapter.notifyItemChanged(position)
            }
        })
        // Adapterの内容がRecyclerViewのサイズに影響しない場合はtrueにするとパフォーマンスアップ
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        recyclerView.adapter = adapter

        // 区切り線を設定
        val separateLine = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(separateLine)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DoneFragment().apply {
            }
    }
}