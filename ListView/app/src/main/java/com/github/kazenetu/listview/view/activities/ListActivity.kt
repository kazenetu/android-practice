package com.github.kazenetu.listview.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.kazenetu.listview.R
import com.github.kazenetu.listview.databinding.ActivityListBinding
import com.github.kazenetu.listview.view.fragments.DoneFragment
import com.github.kazenetu.listview.view.fragments.TodoFragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * メインActivity
 */
class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    internal companion object{
        const val EXTRA_POSITION = "INTENT_POSITION"
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    /**
     * Activity生成
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 切り替え用Fragmentインスタンス
        val todoFragment = TodoFragment.newInstance()
        val doneFragment = DoneFragment.newInstance()

        // Fragment切り替え
        replaceFragment(todoFragment)

        // BottomNavigationの設定
        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_todo ->{
                    replaceFragment(todoFragment)
                }
                R.id.navigation_done ->{
                    replaceFragment(doneFragment)
                }
                R.id.navigation_license ->{
                    val intent = Intent(this, OssLicensesMenuActivity::class.java)
                    startActivity(intent)
                }
            }

            return@OnNavigationItemSelectedListener true
        })
    }

    /**
     * Fragment切り替え
     */
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}