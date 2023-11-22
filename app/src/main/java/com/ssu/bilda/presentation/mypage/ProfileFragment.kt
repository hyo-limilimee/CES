package com.ssu.bilda.presentation.mypage

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.mikephil.charting.charts.RadarChart
import com.ssu.bilda.R

class ProfileFragment : Fragment() {

    private lateinit var radarChart: RadarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        radarChart = view.findViewById(R.id.mapsearchdetail_radar_chart)
//        makeChart()

        return view
    }

//    private fun dataValue(): ArrayList<RadarEntry> {
//        val dataVals = ArrayList<RadarEntry>()
//        dataVals.add(RadarEntry(bigMartList.size.toFloat()))
//        dataVals.add(RadarEntry(gs24List.size.toFloat()))
//        dataVals.add(RadarEntry(schoolList.size.toFloat()))
//        dataVals.add(RadarEntry(academyList.size.toFloat()))
//        dataVals.add(RadarEntry(subwayList.size.toFloat()))
//        dataVals.add(RadarEntry(bankList.size.toFloat()))
//        dataVals.add(RadarEntry(hospitalList.size.toFloat()))
//        dataVals.add(RadarEntry(pharmacyList.size.toFloat()))
//        dataVals.add(RadarEntry(cafeList.size.toFloat()))
//        return dataVals
//    }
//
//    private fun makeChart() {
//        val dataSet = RadarDataSet(dataValue(), "DATA")
//        dataSet.color = Color.BLUE
//
//        val data = RadarData()
//        data.addDataSet(dataSet)
//        val labels = arrayOf("대형마트", "편의점", "학교", "학원", "지하철", "은행", "병원", "약국", "카페")
//        val xAxis: XAxis = radarChart.xAxis
//        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
//        radarChart.data = data


//    }


}
