package com.github.kazenetu.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    /**
     * Activity生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // リストセット
        val adapter = ViewAdapter(createDataList(), object:ViewAdapter.ItemClickListener {
            /**
             * アイテムクリックイベント
             */
            override fun onItemClick(view: View, position: Int, value:RowItem) {
                Toast.makeText(applicationContext, "$position が押されました", Toast.LENGTH_SHORT).show()
            }
        })
        // Adapterの内容がRecyclerViewのサイズに影響しない場合はtrueにするとパフォーマンスアップ
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
    }

    /**
     * ダミーデータ作成
     */
    private fun createDataList(): List<RowItem> {

        val dataList = mutableListOf<RowItem>()
        for (i in 0..49) {
            val data: RowItem = RowItem("タイトル$i","詳細$i")
            dataList.add(data)
        }
        return dataList
    }

}