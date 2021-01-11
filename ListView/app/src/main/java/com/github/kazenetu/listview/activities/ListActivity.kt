package com.github.kazenetu.listview.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.fragments.DoneFragment
import com.github.kazenetu.listview.fragments.TodoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_list.*

/**
 * メインActivity
 */
class ListActivity : AppCompatActivity() {
    internal companion object{
        const val EXTRA_POSITION = "INTENT_POSITION"
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    /**
     * Activity生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // 切り替え用Fragmentインスタンス
        val todoFragment = TodoFragment.newInstance()
        val doneFragment = DoneFragment.newInstance()

        replaceFragment(todoFragment)

        // BottomNavigationの設定
        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_todo ->{
                    replaceFragment(todoFragment)
                }
                R.id.navigation_done ->{
                    replaceFragment(doneFragment)
                }
            }

            return@OnNavigationItemSelectedListener true
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}