package com.github.kazenetu.multiactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window

class SecondActivity : AppCompatActivity() {
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

        setContentView(R.layout.activity_second)
    }
}