package com.github.kazenetu.multiactivity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class FirstActivity : AppCompatActivity() {
    private val button: Button by lazy{findViewById<Button>(R.id.button)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        button.setOnClickListener { view ->
            val intent = Intent(this, SecondActivity::class.java).apply {
                //putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }

    }
}