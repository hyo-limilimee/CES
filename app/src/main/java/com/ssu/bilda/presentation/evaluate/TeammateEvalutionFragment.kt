package com.ssu.bilda.presentation.evaluate

import Scores
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.ssu.bilda.BuildConfig.BASE_URL
import com.ssu.bilda.R
import com.ssu.bilda.data.common.User
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.request.EvaluationRequest
import com.ssu.bilda.data.remote.response.BaseResponse
import com.ssu.bilda.data.service.EvaluationService
import com.ssu.bilda.data.service.UserService
import com.ssu.bilda.presentation.mypage.ProfileFragment
import github.hongbeomi.dividerseekbar.DividerSeekBar
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeammateEvalutionFragment : Fragment() {

    // SeekBar 값 저장을 위한 변수 선언
    private var majorUnderstandingValue: Int = 0
    private var timeAdherenceValue: Int = 0
    private var communicationValue: Int = 0
    private var positivenessValue: Int = 0
    private var responsibilityValue: Int = 0
    private var reMatchingValue: Int = 0

    private val evaluationService = RetrofitImpl.authenticatedRetrofit.create(EvaluationService::class.java)

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
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_major) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue =
                            divisionDataList_major.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // SeekBar의 ID에 따라 해당하는 변수를 업데이트합니다.
                    when (seekBar?.id) {
                        R.id.dividerSeekBar_major -> {
                            majorUnderstandingValue = seekBar.progress
                            Log.d("SeekBarValues", "Major Understanding: $majorUnderstandingValue")
                        }
                    }
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
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_time) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue =
                            divisionDataList_time.minByOrNull { Math.abs(it - progress) } ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // SeekBar의 ID에 따라 해당하는 변수를 업데이트합니다.
                    when (seekBar?.id) {
                        R.id.dividerSeekBar_time -> {
                            timeAdherenceValue = seekBar.progress
                            Log.d("SeekBarValues", "Time Adherence: $timeAdherenceValue")
                        }
                    }
                }
            })
        }

        val dividerSeekBar_communication: DividerSeekBar =
            rootView.findViewById(R.id.dividerSeekBar_communication)

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
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_communication) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue =
                            divisionDataList_communication.minByOrNull { Math.abs(it - progress) }
                                ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // SeekBar의 ID에 따라 해당하는 변수를 업데이트합니다.
                    when (seekBar?.id) {
                        R.id.dividerSeekBar_communication -> {
                            communicationValue = seekBar.progress
                            Log.d("SeekBarValues", "Communication: $communicationValue")
                        }
                    }
                }
            })
        }

        val dividerSeekBar_positiveness: DividerSeekBar =
            rootView.findViewById(R.id.dividerSeekBar_positiveness)

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
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_positiveness) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue =
                            divisionDataList_positiveness.minByOrNull { Math.abs(it - progress) }
                                ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // SeekBar의 ID에 따라 해당하는 변수를 업데이트합니다.
                    when (seekBar?.id) {
                        R.id.dividerSeekBar_positiveness -> {
                            positivenessValue = seekBar.progress
                            Log.d("SeekBarValues", "Positiveness: $positivenessValue")
                        }
                    }
                }
            })
        }

        val dividerSeekBar_responsibility: DividerSeekBar =
            rootView.findViewById(R.id.dividerSeekBar_responsibility)

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
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // 사용자가 시크바를 조작했을 때의 동작
                    if (progress !in divisionDataList_responsibility) {
                        // 허용된 값 외에 다른 값이 선택된 경우 특정 값으로 자동으로 이동
                        val nearestValue =
                            divisionDataList_responsibility.minByOrNull { Math.abs(it - progress) }
                                ?: 0
                        seekBar?.progress = nearestValue
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 사용자가 조작을 시작할 때의 동작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // SeekBar의 ID에 따라 해당하는 변수를 업데이트합니다.
                    when (seekBar?.id) {
                        R.id.dividerSeekBar_responsibility -> {
                            responsibilityValue = seekBar.progress
                            Log.d("SeekBarValues", "Responsibility: $responsibilityValue")
                        }
                    }
                }
            })
        }

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
            handleReMatchingValue(100)

            submitEvaluation()

            dialog.dismiss() // 대화 상자 닫기

        }
        builder.setNegativeButton("아니오") { dialog, which ->
            // "취소" 버튼 클릭 처리 (취소 동작)
            handleReMatchingValue(0)

            submitEvaluation()

            dialog.dismiss() // 대화 상자 닫기
        }

        // 대화 상자 생성 및 표시
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun handleReMatchingValue(value: Int) {
        // Store the value in reMatchingValue variable
        reMatchingValue = value

        // You can use the value (100 or 0) as needed in your code
        when (value) {
            100 -> {
                // Handle when the user selects "네" (100)
                // Example: Perform actions for re-matching with value 100
                Log.d("ReMatchingValue", "Re-matching with value 100")
            }

            0 -> {
                // Handle when the user selects "아니오" (0)
                // Example: Perform actions for not re-matching with value 0
                Log.d("ReMatchingValue", "Not re-matching with value 0")
            }
            // Handle other cases if needed
        }
    }

    private fun submitEvaluation() {
        // 수집한 데이터로 Scores 객체를 만듭니다.
        val evaluationResult = Scores(
            majorUnderstandingValue,
            timeAdherenceValue,
            communicationValue,
            positivenessValue,
            responsibilityValue,
            reMatchingValue
        )

        // EvaluationRequest 객체를 만듭니다.
        val evaluationRequest = EvaluationRequest(
            evaluatedUserId = 6,  // 평가 받는 사용자의 ID
            teamId = 1,
            scores = evaluationResult
        )

        // Log로 값을 출력
        Log.d("EvaluationValues", "Major Understanding: $majorUnderstandingValue")
        Log.d("EvaluationValues", "Time Adherence: $timeAdherenceValue")
        Log.d("EvaluationValues", "Communication: $communicationValue")
        Log.d("EvaluationValues", "Positiveness: $positivenessValue")
        Log.d("EvaluationValues", "Responsibility: $responsibilityValue")
        Log.d("EvaluationValues", "Re-matching: $reMatchingValue")

        // Retrofit을 사용하여 네트워크 요청을 수행합니다.
        val call = evaluationService.sendEvaluationRequest(evaluationRequest)
        call.enqueue(object : retrofit2.Callback<BaseResponse<User>> {
            override fun onResponse(call: Call<BaseResponse<User>>, response: retrofit2.Response<BaseResponse<User>>) {
                Log.d("Retrofit", "평가가 성공적으로 제출되었습니다.")
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                Log.e("Retrofit", "평가 제출 실패", t)
            }
        })
    }

}
