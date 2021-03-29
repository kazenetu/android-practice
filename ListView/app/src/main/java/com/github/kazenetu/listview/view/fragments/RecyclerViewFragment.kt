package com.github.kazenetu.listview.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.databinding.FragmentRecyclerViewBinding
import com.github.kazenetu.listview.domain.domain.TodoItemInterface
import com.github.kazenetu.listview.view.activities.DetailActivity
import com.github.kazenetu.listview.view.activities.ListActivity
import com.github.kazenetu.listview.view.recyclerView.RowItem
import com.github.kazenetu.listview.view.recyclerView.ViewAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class RecyclerViewFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    /**
     * リストビューのインスタンス
     */
    private lateinit var recyclerView: RecyclerView

    /**
     * RecyclerView.Adapterのインスタンス
     */
    protected lateinit var adapter: ViewAdapter

    /**
     * 詳細画面の連携処理用
     */
    private lateinit var observer: CustomLifecycleObserver

    /**
     * 詳細画面の遷移フラグ
     */
    private var isMovedDetail:Boolean = false

    protected  fun base() = binding.root

    /**
     * fragment生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer = CustomLifecycleObserver(requireActivity().activityResultRegistry, this.javaClass.name) {
            isMovedDetail = false
        }
        lifecycle.addObserver(observer)
    }

    /**
     * UI描画
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)

        val view = binding.root

        recyclerView = binding.recyclerList

        // リストセット
        adapter = ViewAdapter(requireActivity(), object: ViewAdapter.ItemClickListener {

            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value: TodoItemInterface) {
                onItemClickEvent(view, position, value)
            }

            /**
             * アイテム長押し
             */
            override fun onItemLongClickListener(view: View, position: Int, value: TodoItemInterface): Boolean {
                return onItemLongClickEvent(view, position, value)
            }

            /**
             * Doneボタン
             */
            override fun onItemDoneClick(view: View, position: Int, value: TodoItemInterface) {
                onItemDoneClickEvent(view, position, value)
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

    protected open fun onItemClickEvent(view: View, position: Int, value: TodoItemInterface){
    }

    protected open fun onItemLongClickEvent(view: View, position: Int, value: TodoItemInterface): Boolean {
        return true
    }

    protected open fun onItemDoneClickEvent(view: View, position: Int, value: TodoItemInterface) {
    }

    /**
     * UI削除
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 詳細画面呼び出し
     */
    protected fun callDetail(position: Int, value: RowItem){

        // 遷移済みの場合はキャンセル
        if(isMovedDetail) return

        // 遷移済みに設定
        isMovedDetail = true

        // 遷移処理
        val intent = Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(ListActivity.EXTRA_POSITION,position)
            putExtra(ListActivity.EXTRA_DATA,value)
        }
        observer.start(intent)

        // 遷移アニメーション設定
        requireActivity().overridePendingTransition(R.anim.list_in, R.anim.list_out)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecyclerViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            RecyclerViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}