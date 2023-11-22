package com.ssu.bilda.presentation.mypage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.ssu.bilda.R


class MyInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_my_info, container, false)

        val saveButton: FrameLayout = rootView.findViewById(R.id.tv_log_out)

        // "저장" 버튼에 클릭 리스너 설정
        saveButton.setOnClickListener {
            // "저장" 버튼을 클릭했을 때 대화 상자 표시
            showSaveDialog()
        }

        return rootView
    }

    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("") // 대화 상자의 제목 설정
        builder.setMessage("정말 로그아웃 하시겠습니까?") // 메시지 설정

        // 대화 상자에 버튼 추가
        builder.setPositiveButton("네") { dialog, which ->
            dialog.dismiss() // 대화 상자 닫기
        }

        builder.setNegativeButton("아니오") { dialog, which ->
            // "취소" 버튼 클릭 처리 (취소 동작)
            dialog.dismiss() // 대화 상자 닫기
        }

        // 대화 상자 생성 및 표시
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}