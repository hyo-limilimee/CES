package com.ssu.bilda.presentation.evaluate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
import com.ssu.bilda.presentation.mypage.ProfileFragment
import github.hongbeomi.dividerseekbar.DividerSeekBar

class TeammateEvalutionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_teammate_evalution, container, false)

        val rightArrowBtn: FrameLayout =
            rootView.findViewById(R.id.fl_evaluation_black_left_arrow_btn)

        // 뒤로가기 화살표 클릭시 처리
        rightArrowBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val profileFragment = ProfileFragment()

            // main_layout에 homeFragment로 transaction 한다.
            transaction.replace(R.id.fl_content, SubjectStatusFragment())

            // 꼭 commit을 해줘야 바뀐다.
            transaction.commit()
        }

        //시크바에 대한 처리
        // 시크바 초기화
        val dividerSeekBar = DividerSeekBar(requireContext()).apply {
            max = 100
            setOffActivatedEvent()
            setOnActivatedEvent()
            setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
            setTextInterval(10)
            setTextColor(R.color.black)
            setSeaLineColor(R.color.ssblue)
            setSeaLineStrokeWidth(R.dimen.dp_1)
            setDividerInterval(10)
            setDividerColor(R.color.ssblue)
            setDividerStrokeWidth(R.dimen.dp_1)
            setActiveMode(DividerSeekBar.ACTIVE_MODE_MINIMUM)
            setActivateTargetValue(10)
        }

        // Set additional properties after initializing the DividerSeekBar
        dividerSeekBar.setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
        dividerSeekBar.setTextInterval(10)
        dividerSeekBar.setDividerInterval(10)
        dividerSeekBar.setDividerStrokeWidth(R.dimen.dp_1)



        //저장 누르는 버튼에 대한 처리
        // "저장" 버튼을 위한 FrameLayout 찾기
        val saveButton: FrameLayout = rootView.findViewById(R.id.fl_blue_save_btn)

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
        builder.setMessage("이 팀원과 다시 한 번 팀플 하시겠습니까?") // 메시지 설정

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
