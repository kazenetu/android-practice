package com.github.kazenetu.listview

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val title: EditText by lazy { editTitle }
    private val description: EditText by lazy { editDescription }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val rowItem = intent.getSerializableExtra(ListActivity.EXTRA_DATA) as RowItem
        title.setText(rowItem?.title)
        description.setText(rowItem?.detail)
    }

    /**
     * 親Activityに戻るイベント
     */
    override fun onSupportNavigateUp(): Boolean {
        // Backを実行
        onBackPressed()
        return true
    }

    /**
     * 戻るボタン
     */
    override fun onBackPressed()
    {
        super.onBackPressed()
        overridePendingTransition( R.anim.detail_in,R.anim.detail_out)
    }

}