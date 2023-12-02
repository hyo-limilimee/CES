package com.ssu.bilda.presentation.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ssu.bilda.R
import com.ssu.bilda.data.common.ScoreItem
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.UserSharedPreferences
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

        // 전공 정보 설정
        val tvMajorName: TextView = view.findViewById(R.id.tv_major_name)
        val userDep = UserSharedPreferences.getUserDep(requireContext())
        tvMajorName.text = userDep?.displayName ?: "" // null 체크 후 값 설정


        // 사용자 정보 설정
        val tvProfileName: TextView = view.findViewById(R.id.tv_profile_name)
        tvProfileName.text = "${UserSharedPreferences.getUserStId(requireContext())} ${
            UserSharedPreferences.getUserName(requireContext())
        }"

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
                        val radarEntries = listOf(
                            RadarEntry(scores[0].averageScore.toFloat()), // 전공 이해도
                            RadarEntry(scores[1].averageScore.toFloat()), // 시간 준수
                            RadarEntry(scores[2].averageScore.toFloat()), // 의사소통 능력
                            RadarEntry(scores[3].averageScore.toFloat()), // 적극성
                            RadarEntry(scores[4].averageScore.toFloat())  // 책임감
                        )

                        val radarDataSet = RadarDataSet(radarEntries, "나의 점수")
                        radarDataSet.setColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.sblue
                            )
                        )
                        radarDataSet.lineWidth = 2f

                        val radarData = RadarData()
                        radarData.addDataSet(radarDataSet)

                        radarChart.data = radarData

                        // Rematching 항목 찾기
                        val rematchingScore = scores.find { it.evaluationItemName == "재매칭 희망도" }

                        // Rematching 항목이 있으면 해당 값을 TextView에 설정
                        if (rematchingScore != null) {
                            val tvRematchingPercent: TextView =
                                view.findViewById(R.id.tv_profile_rematch_percent_amount)
                            tvRematchingPercent.text = rematchingScore.averageScore.toString()
                        }

                        // 리사이클러뷰에 표시할 데이터
                        val receivedEvaluationList = myPageResponse?.scoreItems ?: emptyList()

                        // 리사이클러뷰 어댑터 생성
                        val adapter = ReceivedEvaluationAdapter(receivedEvaluationList)

                        // 리사이클러뷰 참조
                        val recyclerView: RecyclerView =
                            view.findViewById(R.id.rv_recieved_evaluation)

                        // 리사이클러뷰에 어댑터 설정
                        recyclerView.adapter = adapter


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

    // 받은 평가 리싸이클러뷰
    class ReceivedEvaluationAdapter(private val dataList: List<ScoreItem>) :
        RecyclerView.Adapter<ReceivedEvaluationAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvCommentNum: TextView = itemView.findViewById(R.id.tv_profile_evaluation_comment_num)
            val tvSubject: TextView = itemView.findViewById(R.id.tv_profile_evaluation_subject)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rcv_profile_recieved_evaluation, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataList[position]

            // highScoreCount 값을 TextView에 설정
            holder.tvCommentNum.text = item.highScoreCount.toString()

            // EvaluatedItemName을 TextView에 설정
            holder.tvSubject.text = when (item.evaluationItemName) {
                "전공 이해도" -> "전공 이해도가 높아요"
                "시간 준수" -> "약속을 잘 지켜요"
                "의사소통 능력" -> "의사소통을 능력이 뛰어나요"
                "적극성" -> "적극적으로 참여해요"
                "책임감" -> "책임감이 뛰어나요"
                "재매칭 희망도" -> "또 함께하고 싶어요"
                else -> item.evaluationItemName
            }
        }
        override fun getItemCount(): Int {
            return dataList.size
        }
    }
}