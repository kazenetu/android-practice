package com.github.kazenetu.multiactivity

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import android.view.Window.*
import android.widget.Button

class FirstActivity : AppCompatActivity() {
    private val button: Button by lazy{findViewById<Button>(R.id.button)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // エフェクト設定
        if(EffectViewModel.getInstance().useEffect){
            with(window){
                requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
                enterTransition = Explode()
                exitTransition = Explode()
            }
        }else{
            overridePendingTransition(0,0)
        }

        setContentView(R.layout.activity_first)

        button.setOnClickListener { view ->
            val intent = Intent(this, SecondActivity::class.java).apply {
                //putExtra(EXTRA_MESSAGE, message)
            }
            if(EffectViewModel.getInstance().useEffect) {
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            else{
                startActivity(intent)
            }
        }

    }
}