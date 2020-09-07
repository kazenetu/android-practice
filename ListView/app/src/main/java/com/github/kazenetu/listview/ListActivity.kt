package com.github.kazenetu.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // リストセット
        val adapter = ViewAdapter(createDataList())
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