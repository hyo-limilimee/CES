package com.ssu.bilda.presentation.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.service.MyPageService
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var radarChart: RadarChart

    private val myPageService: MyPageService by lazy {
        RetrofitImpl.authenticatedRetrofit.create(MyPageService::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // mp 차트
        radarChart = view.findViewById(R.id.mapsearchdetail_radar_chart)

        val list: ArrayList<RadarEntry> = ArrayList()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = myPageService.getMyPage()

                // API 응답 처리
                if (response.isSuccessful) {
                    val myPageResponse = response.body()

                    // 로그로 각 항목 출력
                    Log.d("마이페이지 정보", "userId: ${myPageResponse?.userId}")
                    Log.d("마이페이지 정보", "userName: ${myPageResponse?.userName}")

                    // RadarChart에 점수를 채우기
                    val scores = myPageResponse?.scoreItems
                    if (scores != null && scores.size >= 5) {
                        for ((index, score) in scores.withIndex()) {
                            Log.d("마이페이지 정보", "evaluationItemName[$index]: ${score.evaluationItemName}")
                            Log.d("마이페이지 정보", "averageScore[$index]: ${score.averageScore}")
                            Log.d("마이페이지 정보", "highScoreCount[$index]: ${score.highScoreCount}")

                            // 나머지 처리...
                        }

                        val radarEntries = listOf(
                            RadarEntry(scores[0].averageScore.toFloat()), // MAJOR
                            RadarEntry(scores[1].averageScore.toFloat()), // PUNCTUALITY
                            RadarEntry(scores[2].averageScore.toFloat()), // COMMUNICATION
                            RadarEntry(scores[3].averageScore.toFloat()), // PROACTIVITY
                            RadarEntry(scores[4].averageScore.toFloat())  // RESPONSIBILITY
                        )

                        val radarDataSet = RadarDataSet(radarEntries, "나의 점수")
                        radarDataSet.setColors(ContextCompat.getColor(requireContext(), R.color.sblue))
                        radarDataSet.lineWidth = 2f

                        val radarData = RadarData()
                        radarData.addDataSet(radarDataSet)

                        radarChart.data = radarData

                        // API 요청이 성공했을 때 로그
                        Log.d("마이페이지 정보", "마이페이지 정보 받기 성공")
                    } else {
                        // API 요청이 실패했을 때 로그
                        Log.e("마이페이지 정보", "마이페이지 정보 받기 실패")
                    }
                } else {
                    // API 요청이 실패했을 때 로그
                    Log.e("마이페이지 정보", "마이페이지 정보 받기 실패 - ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                // 예외 처리
                Log.e("마이페이지 정보", "마이페이지 정보 받기 중 오류 발생: ${e.message}", e)
            }
        }

        val radarDataSet = RadarDataSet(list, "나의 점수")

        radarDataSet.setColors(ContextCompat.getColor(requireContext(), R.color.sblue))

        radarDataSet.lineWidth = 2f

        val radarData = RadarData()

        radarData.addDataSet(radarDataSet)

        radarChart.data = radarData

        radarChart.description.text = ""

        radarChart.animateY(2000)

        // X축 설정
        val xAxis = radarChart.xAxis
        xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.sgray)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // 각 꼭지점에 대한 레이블을 반환
                val labels = arrayOf("전공 이해도", "시간 준수", "의사소통 능력", "적극성", "책임감")

                // value를 정수로 변환하여 레이블을 가져옴
                val index = value.toInt()

                // 배열 범위를 벗어나지 않도록 조절
                val adjustedIndex = if (index in labels.indices) index else 0

                return labels[adjustedIndex]
            }
        }

        // 레이더 차트 Y축 설정
        val yAxis = radarChart.yAxis
        yAxis.gridColor = ContextCompat.getColor(requireContext(), R.color.sgray)
        yAxis.setDrawLabels(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f

        //  설정 버튼에 대한 화면 전환 처리
        val settingButton: FrameLayout = view.findViewById(R.id.fl_ic_setting_btn)

        settingButton.setOnClickListener { view ->
            replaceFragment(MyInfoFragment())
        }

        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }

}