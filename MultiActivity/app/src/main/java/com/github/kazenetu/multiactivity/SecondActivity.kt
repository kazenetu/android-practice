package com.github.kazenetu.multiactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        with(window) {
            // set transition
            enterTransition = Explode()
            exitTransition = Explode()
        }
    }
}