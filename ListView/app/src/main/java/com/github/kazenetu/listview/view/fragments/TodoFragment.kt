package com.github.kazenetu.listview.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.databinding.FragmentTodoBinding
import com.github.kazenetu.listview.domain.domain.TodoItemInterface
import com.github.kazenetu.listview.view.activities.ListActivity
import com.github.kazenetu.listview.view.recyclerView.RowItem
import com.github.kazenetu.listview.view.viewmodels.TodoViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

/**
 * TODOリスト用Fragment
 */
class TodoFragment : RecyclerViewFragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    /**
     * 追加ボタン
     */
    private lateinit var actionButton: ExtendedFloatingActionButton

    /**
     * 追加ボタンの展開状況
     */
    private var addButtonExpanded :Boolean = true

    /**
     * 削除ボタン
     */
    private lateinit var actionDeleteButton: ExtendedFloatingActionButton

    /**
     * TodoViewModelのインスタンス
     */
    private val todoViewModel: TodoViewModel by inject()

    /**
     * fragment生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModelの更新監視
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

        lifecycleScope.launchWhenStarted {
            todoViewModel.listItems.collect {
                if(it.isNotEmpty()){
                    if(todoViewModel.shownLoading){
                        delay(500)
                        todoViewModel.shownLoading = false
                    }

                    binding.progress.visibility = View.GONE
                    binding.superConstraintLayout.visibility = View.VISIBLE
                    adapter.setList(it)
                    if(!addButtonExpanded)
                        actionButton.shrink()
                }else{
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * UI描画
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        _binding = FragmentTodoBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.superConstraintLayout.addView(super.base())
        actionButton = binding.addButton
        actionDeleteButton = binding.deleteButton

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

    /**
     * アイテムクリック
     */
    override fun onItemClickEvent(view: View, position: Int, value: TodoItemInterface){
        callDetail(position, RowItem(value.showImage,value.title,value.detail,value.isDone))
    }

    /**
     * アイテム長押し
     */
    override fun onItemLongClickEvent(view: View, position: Int, value: TodoItemInterface): Boolean {
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
    override fun onItemDoneClickEvent(view: View, position: Int, value: TodoItemInterface) {
        todoViewModel.updateDone(position, !value.isDone)
        adapter.notifyItemChanged(position)
    }

    /**
     * スクロール中
     */
    override fun onScrolledEvent(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // スクロール発生、アイコンの縮小
        if(dy != 0 && actionButton.isExtended){
            actionButton.shrink()
        }
        addButtonExpanded = actionButton.isExtended
    }

    /**
     * スクロール停止
     */
    override fun onScrollStateChangedEvent(recyclerView: RecyclerView, newState: Int) {
        // スクロール位置0の場合はアイコン拡張
        if(newState == RecyclerView.SCROLL_STATE_IDLE &&
            recyclerView.computeVerticalScrollOffset() == 0 &&
            !actionButton.isExtended){
            actionButton.extend()
        }
        addButtonExpanded = actionButton.isExtended
    }

    /**
     * 画面遷移先からの戻り値
     */
    override fun onActivityResultEvent(requestCode: Int, resultCode: Int, data: Intent?) {
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

    /**
     * UI削除
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * インスタンス生成
         */
        @JvmStatic
        fun newInstance() =
            TodoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
