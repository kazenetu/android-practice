package com.github.kazenetu.listview.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.databinding.FragmentTodoBinding
import com.github.kazenetu.listview.domain.interfaces.TodoItemInterface
import com.github.kazenetu.listview.view.activities.DetailActivity
import com.github.kazenetu.listview.view.activities.ListActivity
import com.github.kazenetu.listview.view.recyclerView.RowItem
import com.github.kazenetu.listview.view.recyclerView.ViewAdapter
import com.github.kazenetu.listview.view.viewmodels.TodoViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.koin.android.ext.android.inject

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    /**
     * リストビューのインスタンス
     */
    private lateinit var recyclerView: RecyclerView

    /**
     * 追加ボタン
     */
    private lateinit var actionButton: ExtendedFloatingActionButton

    /**
     * 削除ボタン
     */
    private lateinit var actionDeleteButton: ExtendedFloatingActionButton

    /**
     * TodoViewModelのインスタンス
     */
    private val todoViewModel: TodoViewModel by inject()
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
            activityResult(it.resultCode, it.resultCode, it.data)
        }
        lifecycle.addObserver(observer)

        // ViewModelの更新監視
        todoViewModel.listItems.asLiveData().observe(this, {
            adapter.setList(it)
            binding.progress.visibility = View.GONE
        })
        todoViewModel.toggleDeleteImage.asLiveData().observe(this, { (isShow,all) ->
            if(isShow) {
                actionButton.hide()
                actionDeleteButton.show()
            }else{
                actionButton.show()
                actionDeleteButton.hide()
            }
            if(all){
                adapter.notifyDataSetChanged()
            }
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

    /**
     * 詳細から更新イベント
     */
    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 未遷移に設定
        isMovedDetail = false

        // 削除フラグを非表示にする
        todoViewModel.hideAllDeleteImage()

        Log.d("result","${requestCode},${resultCode}")

        if(resultCode != AppCompatActivity.RESULT_OK){
            return
        }

        val position = data?.getIntExtra(ListActivity.EXTRA_POSITION,-1)
        val row = data?.getParcelableExtra<RowItem>(ListActivity.EXTRA_DATA) as RowItem

        if(position == null ){
            return
        }

        todoViewModel.update(position,row)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentTodoBinding.inflate(inflater, container, false)

        val view = binding.root

        recyclerView = binding.recyclerList
        actionButton = binding.addButton
        actionDeleteButton = binding.deleteButton

        // リストセット
        adapter = ViewAdapter(activity?.applicationContext!!, object: ViewAdapter.ItemClickListener {

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
                if(value.showImage){
                    todoViewModel.hideDeleteImage(position)
                }
                else{
                    todoViewModel.showDeleteImage(position)
                }
                adapter.notifyItemChanged(position)
                return true
            }

            /**
             * Doneボタン
             */
            override fun onItemDoneClick(view: View, position: Int, value: TodoItemInterface) {
                todoViewModel.updateDone(position, !value.isDone)
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

        // スクロール監視
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy != 0 && actionButton.isExtended){
                    actionButton.shrink()
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                    recyclerView.computeVerticalScrollOffset() == 0 &&
                    !actionButton.isExtended){
                    actionButton.extend()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        // 追加ボタンイベント
        actionButton.setOnClickListener {
            callDetail(-1, RowItem(false,"", "",false))
        }

        // 削除ボタンイベント
        actionDeleteButton.setOnClickListener {
            todoViewModel.deleteAll()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TodoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
