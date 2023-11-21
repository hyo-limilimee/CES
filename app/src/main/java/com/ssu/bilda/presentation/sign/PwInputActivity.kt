package com.ssu.bilda.presentation.sign

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssu.bilda.databinding.ActivityPwInputBinding

class PwInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPwInputBinding
    private var pw = ""
    private var rePw = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSignupPw.addTextChangedListener(pwTextWatcher)
        binding.etSignupRepw.addTextChangedListener(rePwTextWatcher)

        // 입력한 비밀번호들 일치하는 경우에만 다음 버튼 활성화
        binding.btnSignupPwnext.isEnabled = false
        
        binding.btnSignupPwnext.setOnClickListener {
            if (pw == rePw) {
                val intent = Intent(this, MyInfoInputActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val pwTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            pw = s.toString()
            pwMatch()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private val rePwTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            rePw = s.toString()
            pwMatch()
            checkPwEquality()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    // 비밀번호 조건 충족 여부 체크
    private fun pwMatch() {
        val pwMatch = pw == rePw && isValidPassword(pw)
        binding.btnSignupPwnext.isEnabled = pwMatch

        binding.tvSignupPwguide.visibility =
            if (pw.isNotEmpty() && pw.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$".toRegex())) View.INVISIBLE else View.VISIBLE
    }

    private fun isValidPassword(pw: String): Boolean {
        val pwPattern = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$".toRegex()
        val isValid = pw.matches(pwPattern)

        // et_signup_pw_guide가시성 관련
        binding.tvSignupPwguide.visibility =
            if (pw.isNotEmpty() && pwPattern.matches(pw)) View.INVISIBLE else View.VISIBLE

        // tv_signup_pw_check 가시성 관련
        binding.tvSignupPwCheck.visibility = if (isValid) View.INVISIBLE else View.VISIBLE

        return isValid
    }

    private fun checkPwEquality() {
        val pw = binding.etSignupPw.text.toString()
        val rePw = binding.etSignupRepw.text.toString()
        val pwMatch = pw == rePw

        // tv_signup_pw_check 가시성 관련
        binding.tvSignupPwCheck.visibility = if (pwMatch) View.INVISIBLE else View.VISIBLE
    }
}
