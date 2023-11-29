package com.ssu.bilda.presentation.evaluate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
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

        // 전공 이해도 시크바
        val dividerSeekBar_major: DividerSeekBar = rootView.findViewById(R.id.dividerSeekBar_major)

        // 10씩 끊기게 설정
        val divisionDataList_major = mutableListOf<Int>()
        for (i in 0..10) {
            divisionDataList_major.add(i * 10)
        }

        dividerSeekBar_major.apply {
            max = 100
            setOffActivatedEvent()
            setOnActivatedEvent()
            setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
            setTextInterval(10)
            setTextColor(R.color.sgray)
            setTextSize(R.dimen.sp_12)
            setSeaLineColor(R.color.sgray)
            setSeaLineStrokeWidth(R.dimen.dp_1)
            setDividerInterval(10)
            setDividerColor(R.color.sgray)
            setDividerStrokeWidth(R.dimen.dp_1)
            setActiveMode(DividerSeekBar.ACTIVE_MODE_MINIMUM)
            setActivateTargetValue(0)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_major) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue = divisionDataList_major.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 끝낼 때의 동작
                }
            })
        }

        // 시간 준수 시크바
        val dividerSeekBar_time: DividerSeekBar = rootView.findViewById(R.id.dividerSeekBar_time)

        // 10씩 끊기게 설정
        val divisionDataList_time = mutableListOf<Int>()
        for (i in 0..10) {
            divisionDataList_time.add(i * 10)
        }

        dividerSeekBar_time.apply {
            max = 100
            setOffActivatedEvent()
            setOnActivatedEvent()
            setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
            setTextInterval(10)
            setTextColor(R.color.sgray)
            setTextSize(R.dimen.sp_12)
            setSeaLineColor(R.color.sgray)
            setSeaLineStrokeWidth(R.dimen.dp_1)
            setDividerInterval(10)
            setDividerColor(R.color.sgray)
            setDividerStrokeWidth(R.dimen.dp_1)
            setActiveMode(DividerSeekBar.ACTIVE_MODE_MINIMUM)
            setActivateTargetValue(0)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_time) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue = divisionDataList_time.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 끝낼 때의 동작
                }
            })
        }

        val dividerSeekBar_communication: DividerSeekBar = rootView.findViewById(R.id.dividerSeekBar_communication)

        // 10씩 끊기게 설정
        val divisionDataList_communication = mutableListOf<Int>()
        for (i in 0..10) {
            divisionDataList_communication.add(i * 10)
        }

        dividerSeekBar_communication.apply {
            max = 100
            setOffActivatedEvent()
            setOnActivatedEvent()
            setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
            setTextInterval(10)
            setTextColor(R.color.sgray)
            setTextSize(R.dimen.sp_12)
            setSeaLineColor(R.color.sgray)
            setSeaLineStrokeWidth(R.dimen.dp_1)
            setDividerInterval(10)
            setDividerColor(R.color.sgray)
            setDividerStrokeWidth(R.dimen.dp_1)
            setActiveMode(DividerSeekBar.ACTIVE_MODE_MINIMUM)
            setActivateTargetValue(0)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_communication) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue = divisionDataList_communication.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 끝낼 때의 동작
                }
            })
        }

        val dividerSeekBar_positiveness: DividerSeekBar = rootView.findViewById(R.id.dividerSeekBar_positiveness)

        // 10씩 끊기게 설정
        val divisionDataList_positiveness = mutableListOf<Int>()
        for (i in 0..10) {
            divisionDataList_positiveness.add(i * 10)
        }

        dividerSeekBar_positiveness.apply {
            max = 100
            setOffActivatedEvent()
            setOnActivatedEvent()
            setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
            setTextInterval(10)
            setTextColor(R.color.sgray)
            setTextSize(R.dimen.sp_12)
            setSeaLineColor(R.color.sgray)
            setSeaLineStrokeWidth(R.dimen.dp_1)
            setDividerInterval(10)
            setDividerColor(R.color.sgray)
            setDividerStrokeWidth(R.dimen.dp_1)
            setActiveMode(DividerSeekBar.ACTIVE_MODE_MINIMUM)
            setActivateTargetValue(0)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_positiveness) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue = divisionDataList_positiveness.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 끝낼 때의 동작
                }
            })
        }

        val dividerSeekBar_responsibility: DividerSeekBar = rootView.findViewById(R.id.dividerSeekBar_responsibility)

        // 10씩 끊기게 설정
        val divisionDataList_responsibility = mutableListOf<Int>()
        for (i in 0..10) {
            divisionDataList_responsibility.add(i * 10)
        }

        dividerSeekBar_responsibility.apply {
            max = 100
            setOffActivatedEvent()
            setOnActivatedEvent()
            setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)
            setTextInterval(10)
            setTextColor(R.color.sgray)
            setTextSize(R.dimen.sp_12)
            setSeaLineColor(R.color.sgray)
            setSeaLineStrokeWidth(R.dimen.dp_1)
            setDividerInterval(10)
            setDividerColor(R.color.sgray)
            setDividerStrokeWidth(R.dimen.dp_1)
            setActiveMode(DividerSeekBar.ACTIVE_MODE_MINIMUM)
            setActivateTargetValue(0)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_responsibility) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue = divisionDataList_responsibility.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 끝낼 때의 동작
                }
            })
        }

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
