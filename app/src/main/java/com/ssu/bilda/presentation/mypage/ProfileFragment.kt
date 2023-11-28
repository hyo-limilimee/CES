package com.ssu.bilda.presentation.mypage

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import com.ssu.bilda.R

class ProfileFragment : Fragment() {

    private lateinit var radarChart: RadarChart
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // mp 차트
        radarChart = view.findViewById(R.id.mapsearchdetail_radar_chart)

        val list: ArrayList<RadarEntry> = ArrayList()

        list.add(RadarEntry(80f))
        list.add(RadarEntry(70f))
        list.add(RadarEntry(100f))
        list.add(RadarEntry(50f))
        list.add(RadarEntry(20f))

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
