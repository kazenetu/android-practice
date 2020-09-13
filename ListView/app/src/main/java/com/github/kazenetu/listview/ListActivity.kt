package com.github.kazenetu.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy { recycler_list }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // リストセット
        val adapter = ViewAdapter(createDataList(), object:ViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int, value:RowItem) {
                Toast.makeText(applicationContext, "$position が押されました", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    private fun createDataList(): List<RowItem> {

        val dataList = mutableListOf<RowItem>()
        for (i in 0..49) {
            val data: RowItem = RowItem("タイトル$i","詳細$i")
            dataList.add(data)
        }
        return dataList
    }

}