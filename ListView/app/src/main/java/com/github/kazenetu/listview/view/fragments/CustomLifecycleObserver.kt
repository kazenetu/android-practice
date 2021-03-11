package com.github.kazenetu.listview.view.fragments

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * LifecycleOwner状態の変化をリッスンするためのコールバッククラス
 */
class CustomLifecycleObserver(private val registry: ActivityResultRegistry, private val keyName:String ,private val callback: ActivityResultCallback<ActivityResult>) :
    DefaultLifecycleObserver {

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    /**
     * 生成
     */
    override fun onCreate(owner: LifecycleOwner) {
        startForResult =
            registry.register(keyName, owner, ActivityResultContracts.StartActivityForResult(),
                callback)
    }

    /**
     * 画面遷移
     */
    fun start(intent: Intent) {
        startForResult.launch(intent)
    }
}