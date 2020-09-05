package com.github.kazenetu.multiactivity

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import android.view.Window.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_first.view.*

class FirstActivity : AppCompatActivity() {
    private val button: Button by lazy{findViewById<Button>(R.id.button)}
    private val checkbox: CheckBox by lazy{findViewById<CheckBox>(R.id.checkBox)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // エフェクト設定
        if(EffectViewModel.getInstance().useEffect){
            with(window){
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                enterTransition = Explode()
                exitTransition = Explode()
            }
        }else{
            overridePendingTransition(0,0)
        }

        setContentView(R.layout.activity_first)

        // チェックボックスの初期値設定
        checkbox.setChecked(EffectViewModel.getInstance().useEffect)

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

        checkbox.setOnClickListener { view ->
            EffectViewModel.getInstance().useEffect = view.checkBox.isChecked

            // トースト通知
            var text = "エフェクトOFF"
            if(view.checkBox.isChecked){
                text = "エフェクトON"
            }
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }
}