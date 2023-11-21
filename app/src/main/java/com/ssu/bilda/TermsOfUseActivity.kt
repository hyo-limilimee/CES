package com.ssu.bilda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssu.bilda.databinding.ActivityTermsOfPrivacyBinding
import com.ssu.bilda.databinding.ActivityTermsOfUseBinding

class TermsOfUseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsOfUseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfUseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭으로 되돌아가기
        binding.btnUseTermsBack.setOnClickListener {
            finish()
        }
        // 버튼이 담긴 영역 클릭으로 되돌아가기
        binding.flUseTermsBack.setOnClickListener {
            finish()
        }
    }
}