package com.github.kazenetu.listview.view.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.view.recyclerView.RowItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val title: EditText by lazy { editTitle }
    private val description: EditText by lazy { editDescription }
    private val changeButton: Button by lazy { button }
    private var rowPosition:Int=-1
    private var isDone:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        rowPosition = intent.getIntExtra(ListActivity.EXTRA_POSITION,-1)
        val rowItem = intent.getParcelableExtra<RowItem>(ListActivity.EXTRA_DATA) as RowItem
        title.setText(rowItem.title)
        description.setText(rowItem.detail)
        isDone = rowItem.isDone
        
        // Doneの場合は表示のみ
        if(isDone) {
            title.freezesText = false
            description.freezesText = false
            button.visibility = View.GONE
        }

        // ボタンの名称を設定
        if(rowPosition >=0 ) {
            button.setText(R.string.Update)
        }

        /**
         * 登録ボタンクリックイベント
         */
        changeButton.setOnClickListener {
            if(title.text.trim().isEmpty()){
                title.requestFocus()
                val softKeyboard:InputMethodManager =  title.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                softKeyboard.showSoftInput(title,0)
                val labelTitleName = labelTitle.text.toString()
                title.error = "${labelTitleName}を入力してください"
                return@setOnClickListener
            }

            val i=intent.apply {
                putExtra(ListActivity.EXTRA_POSITION,rowPosition)
                putExtra(
                    ListActivity.EXTRA_DATA,
                    RowItem(false,title.text.toString(),description.text.toString(),isDone)
                )
            }
            setResult(RESULT_OK,i)
            finish()
            overridePendingTransition(R.anim.detail_in, R.anim.detail_out)
        }
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
        overridePendingTransition(R.anim.detail_in, R.anim.detail_out)

        // 戻る場合はキャンセル
        setResult(RESULT_CANCELED)
        finish()
    }

}