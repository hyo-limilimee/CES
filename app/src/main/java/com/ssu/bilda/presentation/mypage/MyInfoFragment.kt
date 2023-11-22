package com.ssu.bilda.presentation.mypage

import android.app.AlertDialog
import android.content.Intent
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

        val logOutButton: FrameLayout = rootView.findViewById(R.id.fl_tv_log_out)

        // "저장" 버튼에 클릭 리스너 설정
        logOutButton.setOnClickListener {
            // "저장" 버튼을 클릭했을 때 대화 상자 표시
            showLogoutDialog()
        }

        val withdrawalButton: FrameLayout = rootView.findViewById(R.id.fl_tv_withdrawal)

        // "저장" 버튼에 클릭 리스너 설정
        withdrawalButton.setOnClickListener {
            // "저장" 버튼을 클릭했을 때 대화 상자 표시
            showWithdrawalDialog()
        }

        rootView.findViewById<View>(R.id.fl_ic_my_info_pw_change_btn).setOnClickListener {
            // ChangePassWordActivity로 전환하는 코드
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        rootView.findViewById<View>(R.id.fl_ic_my_info_nick_name_change_btn).setOnClickListener {
            // ChangePassWordActivity로 전환하는 코드
            val intent = Intent(activity, ChangeNicknameActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("") // 대화 상자의 제목 설정
        builder.setMessage("정말 로그아웃 하시겠습니까?") // 메시지 설정

        // 대화 상자에 버튼 추가
        builder.setPositiveButton("로그아웃") { dialog, which ->
            // "저장" 버튼 클릭 처리 (저장 동작)
            // 여기에 저장 로직을 추가할 수 있습니다.
            dialog.dismiss() // 대화 상자 닫기
        }

        builder.setNegativeButton("취소") { dialog, which ->
            // "취소" 버튼 클릭 처리 (취소 동작)
            dialog.dismiss() // 대화 상자 닫기
        }

        // 대화 상자 생성 및 표시
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showWithdrawalDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("") // 대화 상자의 제목 설정
        builder.setMessage("정말 탈퇴 하시겠습니까?") // 메시지 설정

        // 대화 상자에 버튼 추가
        builder.setPositiveButton("네") { dialog, which ->
            // "저장" 버튼 클릭 처리 (저장 동작)
            // 여기에 저장 로직을 추가할 수 있습니다.
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