package com.ssu.bilda.presentation.mypage

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.ssu.bilda.R

class ProfileFragment : Fragment() {

    lateinit var radarChart: RadarChart
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // mp 차트
        radarChart = view.findViewById(R.id.fl_mp_chart_my_score)

        val list:ArrayList<RadarEntry> = ArrayList()

        list.add(RadarEntry(100f))
        list.add(RadarEntry(101f))
        list.add(RadarEntry(102f))
        list.add(RadarEntry(103f))
        list.add(RadarEntry(104f))
        
        val radarDataSet = RadarDataSet(list, "List")

        radarDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)

        radarDataSet.lineWidth=2f

        radarDataSet.valueTextSize=14f

        radarDataSet.valueTextColor = Color.RED

        val radarData = RadarData()

        radarData.addDataSet(radarDataSet)

        radarChart.description.text = "Radar Chart"
        
        radarChart.animateY(2000)

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
