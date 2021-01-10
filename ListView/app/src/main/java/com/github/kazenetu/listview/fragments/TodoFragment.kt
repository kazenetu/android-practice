package com.github.kazenetu.listview.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kazenetu.listview.*
import com.github.kazenetu.listview.room.TodoItem
import com.github.kazenetu.listview.viewmodels.TodoViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.fragment_todo.*
import org.koin.android.ext.android.inject

class TodoFragment : Fragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModelの更新監視
        todoViewModel.listItems.observe(this, {
            adapter.setList(it)
            progress.visibility = View.GONE
        })
        todoViewModel.toggleDeleteImage.observe(this, { (isShow,all) ->
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
        startActivityForResult(intent,0)

        // 遷移アニメーション設定
        activity?.overridePendingTransition(R.anim.list_in, R.anim.list_out)
    }

    /**
     * 詳細から更新イベント
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 未遷移に設定
        isMovedDetail = false

        if(requestCode==0 && resultCode== AppCompatActivity.RESULT_OK && data!=null) {
            val position = data.getIntExtra(ListActivity.EXTRA_POSITION,-1)
            val row = data.getParcelableExtra<RowItem>(ListActivity.EXTRA_DATA) as RowItem

            todoViewModel.update(position,row)
        }

        // 削除フラグを非表示にする
        todoViewModel.hideAllDeleteImage()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_todo, container, false)

        recyclerView = view.findViewById(R.id.recycler_list)
        actionButton = view.findViewById(R.id.addButton)
        actionDeleteButton=view.findViewById(R.id.deleteButton)

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
            override fun onItemDoneClick(view: View, position: Int, value: TodoItem) {
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

    companion object {
        @JvmStatic
        fun newInstance() =
            TodoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}