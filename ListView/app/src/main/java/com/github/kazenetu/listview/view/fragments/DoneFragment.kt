package com.github.kazenetu.listview.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.databinding.FragmentDoneBinding
import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.view.activities.DetailActivity
import com.github.kazenetu.listview.view.activities.ListActivity
import com.github.kazenetu.listview.view.recyclerView.RowItem
import com.github.kazenetu.listview.view.recyclerView.ViewAdapter
import com.github.kazenetu.listview.view.viewmodels.DoneViewModel
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 * Use the [DoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

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

    /**
     * 詳細画面の連携処理用
     */
    private lateinit var observer: CustomLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer = CustomLifecycleObserver(requireActivity().activityResultRegistry, this.javaClass.name) {
            isMovedDetail = false
        }
        lifecycle.addObserver(observer)

        // ViewModelの更新監視
        doneViewModel.listItems.asLiveData().observe(this, {
            it.let{adapter.setList(it)}
        })
    }

    /**
     * 詳細画面呼び出し
     */
    private fun callDetail(position: Int, value: RowItem){

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoneBinding.inflate(inflater, container, false)

        val view = binding.root

        recyclerView = binding.recyclerList

        // リストセット
        adapter = ViewAdapter(requireActivity(), object: ViewAdapter.ItemClickListener {

            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value: TodoItemInterface) {
                callDetail(position, RowItem(value.showImage,value.title,value.detail,value.isDone))
            }

            /**
             * アイテム長押し
             */
            override fun onItemLongClickListener(view: View, position: Int, value: TodoItemInterface): Boolean {
                return true
            }

            /**
             * Doneボタン
             */
            override fun onItemDoneClick(view: View, position: Int, value: TodoItemInterface) {
                doneViewModel.updateDone(position, !value.isDone)
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