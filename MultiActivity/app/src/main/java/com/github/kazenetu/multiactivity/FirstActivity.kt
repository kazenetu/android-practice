package com.github.kazenetu.multiactivity

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.view.Window.*
import android.widget.Button

class FirstActivity : AppCompatActivity() {
    private val button: Button by lazy{findViewById<Button>(R.id.button)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        with(window) {
            // set transition
            enterTransition = Explode()
            exitTransition = Explode()
        }

        button.setOnClickListener { view ->
            val intent = Intent(this, SecondActivity::class.java).apply {
                //putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }
}