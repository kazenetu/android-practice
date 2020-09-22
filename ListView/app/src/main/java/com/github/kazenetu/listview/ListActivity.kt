package com.github.kazenetu.listview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*

/**
 * メインActivity
 */
class ListActivity : AppCompatActivity() {
    /**
     * リストビューのインスタンス
     */
    private val recyclerView: RecyclerView by lazy { recycler_list }

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

        val myInstance = this
        // リストセット
        val adapter = ViewAdapter(TodoViewModel.getInstance().items, object:ViewAdapter.ItemClickListener {
            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value:RowItem) {
                val intent = Intent(myInstance, DetailActivity::class.java).apply {
                    putExtra(EXTRA_POSITION,position)
                    putExtra(EXTRA_DATA,value)
                }
                startActivityForResult(intent,0)
                overridePendingTransition(R.anim.list_in, R.anim.list_out)
            }
        })
        // Adapterの内容がRecyclerViewのサイズに影響しない場合はtrueにするとパフォーマンスアップ
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
    }

    /**
     * 詳細から更新イベント
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==0 && resultCode== RESULT_OK && data!=null) {
            val position = data.getIntExtra(EXTRA_POSITION,-1)
            val row = data.getSerializableExtra(EXTRA_DATA) as RowItem
            if(position < 0) return

            TodoViewModel.getInstance().items[position] = row
        }
    }
}