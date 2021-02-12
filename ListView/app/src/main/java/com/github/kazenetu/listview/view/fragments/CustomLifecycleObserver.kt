package com.github.kazenetu.listview.view.fragments

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class CustomLifecycleObserver(private val registry: ActivityResultRegistry, private val callback: ActivityResultCallback<ActivityResult>) :
    DefaultLifecycleObserver {

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        startForResult =
            registry.register("key", owner, ActivityResultContracts.StartActivityForResult(),
                callback)
    }

    fun start(intent: Intent) {
        startForResult.launch(intent)
    }
}