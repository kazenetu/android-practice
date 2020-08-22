package com.github.kazenetu.activityevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*

/**
 * 永続化用ViewModel
 */
class TextViewModel : ViewModel() {
    companion object{
        private var textList:MutableList<String> = mutableListOf<String>()

        private var instance:TextViewModel = ViewModelProvider.NewInstanceFactory().create(TextViewModel::class.java)
        fun getInstance():TextViewModel = instance
    }
    private var text:MutableLiveData<String> = MutableLiveData()
    val publish : LiveData<String> get() = text

    fun add(string: String) {
        textList.add(0,"${getTime()}:$string")
        text.postValue(textList.joinToString(separator = "\n"))
    }

    fun getTime():String {
        val date = Date()
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}

/**
 * メインActivity
 */
class MainActivity : AppCompatActivity() {
    private val textView: TextView by lazy{findViewById<TextView>(R.id.text)}
    lateinit var viewModel :TextViewModel

    /**
     * Activityのインスタンスが作られたときに⼀度だけ呼び出される
     * INITIALIZEDからCREATEDに変わるときに呼ばれる
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // viewlModelインスタンスを取得設定
        viewModel = TextViewModel.getInstance()

        // pus/sub
        viewModel.publish.observe(this, Observer<String> { publish ->
            textView.text = publish
        })

        viewModel.add("onCreate state:" + lifecycle.currentState + "\nExistsBunle:${savedInstanceState!=null}\n-------------------")
    }

    /**
     * 画⾯が表⽰されたタイミングで呼び出される
     * CREATEDからSTARTEDに変わるときに呼ばれる
     */
    override fun onStart() {
        super.onStart()
        viewModel.add("onStart state:" + lifecycle.currentState + "\n-------------------")
    }

    /**
     * 画⾯がフォーカスを持ったタイミングで呼びされる
     * STARTEDからRESUMEDに変わるときに呼ばれる
     */
    override fun onResume() {
        super.onResume()
        viewModel.add("onResume state:" + lifecycle.currentState)
    }

    /**
     * onResumeのあとに呼び出される
     */
    override fun onPostResume() {
        super.onPostResume()
        viewModel.add("onPostResume state:" + lifecycle.currentState)
    }

    /**
     * フォーカスを失うと呼び出される
     * RESUMEDからSTARTEDに変わるときに呼ばれる
     */
    override fun onPause() {
        super.onPause()
        viewModel.add("onPause state:"+lifecycle.currentState)
    }

    /**
     * 画面が表示されなくなると呼び出される
     * STARTEDからCREATEDに変わるときに呼ばれる
     */
    override fun onStop() {
        super.onStop()
        viewModel.add("onStop state:"+lifecycle.currentState)
    }

    /**
     * インスタンスが破棄されると呼び出される
     * Destroyedに変わる
     */
    override fun onDestroy() {
        super.onDestroy()
        viewModel.add("onDestroy state:"+lifecycle.currentState)
    }
}