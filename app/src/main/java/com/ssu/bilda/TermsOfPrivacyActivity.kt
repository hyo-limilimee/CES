package com.ssu.bilda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssu.bilda.databinding.ActivityMyInfoInputBinding
import com.ssu.bilda.databinding.ActivityTermsBinding
import com.ssu.bilda.databinding.ActivityTermsOfPrivacyBinding

class TermsOfPrivacyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsOfPrivacyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭으로 되돌아가기
        binding.btnPrivacyTermsBack.setOnClickListener {
            finish()
        }
        // 버튼이 담긴 영역 클릭으로 되돌아가기
        binding.flPrivacyTermsBack.setOnClickListener {
            finish()
        }
    }
}