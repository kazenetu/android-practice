package com.github.kazenetu.listview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
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