package com.ssu.bilda.presentation

import com.ssu.bilda.presentation.mypage.ProfileFragment
import com.ssu.bilda.presentation.evaluate.ProjectStatusFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssu.bilda.presentation.home.HomeFragment
import com.ssu.bilda.R
import com.ssu.bilda.presentation.evaluate.SubjectStatusFragment
import com.ssu.bilda.presentation.mypage.MyInfoFragment

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

    fun changeToMyInfoFragment() {
        val myInfoFragment = SubjectStatusFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, myInfoFragment)
            .addToBackStack(null) // 백 스택에 추가하여 뒤로 가기 버튼으로 이전 프래그먼트로 돌아갈 수 있도록 함
            .commit()
    }
}
