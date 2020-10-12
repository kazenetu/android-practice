package com.github.kazenetu.listview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.*
import kotlinx.android.synthetic.main.activity_list.*


/**
 * メインActivity
 */
class ListActivity : AppCompatActivity() {
    /**
     * リストビューのインスタンス
     */
    private val recyclerView: RecyclerView by lazy { recycler_list }

    /**
     * 追加ボタン
     */
    private val ActionButton: ExtendedFloatingActionButton by lazy { addButton }

    /**
     * TodoViewModelのインスタンス
     */
    private val viewModel:TodoViewModel by lazy{TodoViewModel.getInstance()}

    /**
     * RecyclerView.Adapterのインスタンス
     */
    private lateinit var adapter: ViewAdapter

    internal companion object{
        const val EXTRA_POSITION = "INTENT_POSITION"
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    /**
     * Activity生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // リストセット
        adapter = ViewAdapter(viewModel.listItems, object:ViewAdapter.ItemClickListener {
            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value:RowItem) {
                callDetail(position, value)
            }

            /**
             * アイテム長押し
             */
            override fun OnItemLongClickListener(view: View, position: Int, value:RowItem): Boolean {
                value.showImage =!value.showImage
                TodoViewModel.getInstance().update(position, value)

                return true
            }
        })
        // Adapterの内容がRecyclerViewのサイズに影響しない場合はtrueにするとパフォーマンスアップ
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter

        // スクロール監視
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy != 0 && ActionButton.isExtended){
                    ActionButton.shrink()
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                    recyclerView.computeVerticalScrollOffset() == 0 &&
                    !ActionButton.isExtended){
                    ActionButton.extend()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        // ViewModelの更新監視
        viewModel.update.observe(this, Observer { index ->
            if(index < 0) {
                adapter.notifyItemInserted(0)
                recyclerView.smoothScrollToPosition(0)
            }else{
                adapter.notifyItemChanged(index)
            }
        })
        viewModel.delete.observe(this, Observer { index ->
            if(index < 0) {
                adapter.notifyDataSetChanged()
            }else{
                adapter.notifyItemRemoved(index)
            }
        })

        // 追加ボタンイベント
        ActionButton.setOnClickListener {_->
            callDetail(-1, RowItem(false,"", ""))
        }
    }

    /**
     * 詳細画面呼び出し
     */
    private fun callDetail(position: Int, value:RowItem){
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_POSITION,position)
            putExtra(EXTRA_DATA,value)
        }
        startActivityForResult(intent,0)
        overridePendingTransition(R.anim.list_in, R.anim.list_out)
    }

    /**
     * 詳細から更新イベント
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==0 && resultCode== RESULT_OK && data!=null) {
            val position = data.getIntExtra(EXTRA_POSITION,-1)
            val row = data.getSerializableExtra(EXTRA_DATA) as RowItem

            TodoViewModel.getInstance().update(position,row)
        }
    }
}