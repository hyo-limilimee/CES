package com.ssu.bilda.presentation

import ProfileFragment
import ProjectStatusFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssu.bilda.presentation.home.HomeFragment
import com.ssu.bilda.R

class BnvActivity : AppCompatActivity() {
    private val fl: FrameLayout by lazy {
        findViewById(R.id.fl_content)
    }

    private val bn: BottomNavigationView by lazy {
        findViewById(R.id.bnv_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bnv)

        bn.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bnv_home -> replaceFragment(HomeFragment())
                R.id.bnv_team -> replaceFragment(ProjectStatusFragment())
                R.id.bnv_person -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}
