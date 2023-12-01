package com.ssu.bilda.presentation.mypage

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.App
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.presentation.sign.SignInActivity

class MyInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_my_info, container, false)

        // 좌상단 뒤로가기 버튼 설정
        val rightArrowBtn: FrameLayout = rootView.findViewById(R.id.fl__my_info_black_left_arrow_btn)

        rightArrowBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val profileFragment = ProfileFragment()

            // main_layout에 homeFragment로 transaction 한다.
            transaction.replace(R.id.fl_content, profileFragment)

            // 꼭 commit을 해줘야 바뀐다.
            transaction.commit()
        }

        // 사용자 이름 불러오기
        val tvUserName: TextView = rootView.findViewById(R.id.tv_my_info_name_input)
        val userName = UserSharedPreferences.getUserName(requireContext())
        // 사용자 이름이 있다면 TextView에 설정
        if (userName.isNotEmpty()) {
            tvUserName.text = userName
        }

        // 사용자 학번 불러오기
        val tvUserSId: TextView = rootView.findViewById(R.id.tv_my_info_student_id_input)
        val userSId = UserSharedPreferences.getUserStId(requireContext())

        if (userName.isNotEmpty()) {
            tvUserSId.text = userSId
        }

        // 사용자 닉네임 불러오기
        val tvUserNickname: TextView = rootView.findViewById(R.id.tv_my_info_nick_name_input)
        val userNickname = UserSharedPreferences.getUserNickname(requireContext())

        if (userName.isNotEmpty()) {
            tvUserNickname.text = userNickname
        }

        // 사용자 이메일 불러오기
        val tvUserEmail: TextView = rootView.findViewById(R.id.tv_my_info_email_input)
        val userEmail = UserSharedPreferences.getUserEmail(requireContext())
        // 사용자 이메일이 있다면 TextView에 설정
        if (userEmail.isNotEmpty()) {
            tvUserEmail.text = userEmail
        }

        // 사용자 비밀번호 불러오기
        val tvUserPw: TextView = rootView.findViewById(R.id.tv_my_info_pw_input)
        val userPw = UserSharedPreferences.getUserPw(requireContext())

        if (userPw.isNotEmpty()) {
            tvUserPw.text = userPw
        }

        val logOutButton: FrameLayout = rootView.findViewById(R.id.fl_tv_log_out)

        // "로그아웃" 버튼에 클릭 리스너 설정
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


        builder.setPositiveButton("로그아웃") { dialog, which ->
            // "로그아웃" 버튼을 클릭했을 때 logout 메서드 호출
            logout()
            dialog.dismiss() // 다이얼로그 닫기
        }

        builder.setNegativeButton("취소") { dialog, which ->
            // "취소" 버튼 클릭 처리 (취소 동작)
            dialog.dismiss() // 대화 상자 닫기
        }

        // 대화 상자 생성 및 표시
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun logout() {
        // 저장된 액세스 토큰과 리프래시 토큰을 지웁니다.
        App.token_prefs.accessToken = null
        App.token_prefs.refreshToken = null

        // 여기에서 추가적인 로그아웃 작업을 수행합니다.

        // 로그인 화면으로 리다이렉트
        startActivity(Intent(context, SignInActivity::class.java))
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