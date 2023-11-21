package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssu.bilda.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 서비스 이용약관 페이지 이동_버튼
        binding.btnTermsMoveService.setOnClickListener {
            val mIntent = Intent(this, TermsOfUseActivity::class.java)
            startActivity(mIntent)
        }
        // 서비스 이용약관 페이지 이동_영역
        binding.llTermsL1.setOnClickListener {
            val mIntent = Intent(this, TermsOfUseActivity::class.java)
            startActivity(mIntent)
        }
        // 개인정보처리방침 페이지 이동_버튼
        binding.btnTermsMovePrivacy.setOnClickListener {
            val mIntent = Intent(this, TermsOfPrivacyActivity::class.java)
            startActivity(mIntent)
        }
        // 개인정보처리방침 페이지 이동_영역
        binding.llTermsL2.setOnClickListener {
            val mIntent = Intent(this, TermsOfPrivacyActivity::class.java)
            startActivity(mIntent)
        }

        //모두 동의 버튼 클릭
        binding.btnTermsAgree.setOnClickListener {
            val intent = Intent(this, EmailInputActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
