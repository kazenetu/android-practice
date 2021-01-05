package com.github.kazenetu.listview.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    /**
     * 詳細画面の遷移フラグ
     */
    private var isMovedDetail:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModelの更新監視
        doneViewModel.listItems.observe(this, {
            it.let{adapter.setList(it)}
        })
    }

    /**
     * 詳細画面呼び出し
     */
    private fun callDetail(position: Int, value:RowItem){

        // 遷移済みの場合はキャンセル
        if(isMovedDetail) return

        // 遷移済みに設定
        isMovedDetail = true

        // 遷移処理
        val intent = Intent(activity, DetailActivity::class.java).apply {
            putExtra(ListActivity.EXTRA_POSITION,position)
            putExtra(ListActivity.EXTRA_DATA,value)
        }
        startActivity(intent)

        // 遷移アニメーション設定
        activity?.overridePendingTransition(R.anim.list_in, R.anim.list_out)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_done, container, false)

        recyclerView = view.findViewById(R.id.recycler_list)

        // リストセット
        adapter = ViewAdapter(activity?.applicationContext!!, object:ViewAdapter.ItemClickListener {
            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value: TodoItem) {
                callDetail(position, RowItem(value.showImage,value.title,value.detail,value.isDone))
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
        recyclerView.layoutManager = LinearLayoutManager(context)
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