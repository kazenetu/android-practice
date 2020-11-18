package com.github.kazenetu.listview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.kazenetu.listview.fragments.TodoFragment

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

        replaceFragment(TodoFragment.newInstance())

    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}