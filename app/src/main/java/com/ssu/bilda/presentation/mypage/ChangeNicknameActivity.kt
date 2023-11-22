package com.ssu.bilda.presentation.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ssu.bilda.R

class ChangeNicknameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_nickname)

        val llArrowAndChangePw: View = findViewById(R.id.ll_ic_arrow_and_change_nickname)
        // ll_ic_arrow_and_change_nickname을 클릭할 때의 이벤트 리스너 설정
        llArrowAndChangePw.setOnClickListener {
            // MyInfoFragment로 돌아가기
            onBackPressed()
        }
    }
}