package com.ssu.bilda.presentation.teambuild

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.RetrofitImpl
import com.ssu.bilda.data.remote.request.TeamCreateRequest
import com.ssu.bilda.data.service.TeamService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class TeamBuildWritingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_team_build_writing, container, false)

        val flIcSelectTerm: FrameLayout = view.findViewById(R.id.fl_ic_select_term)
        val tvSelectedDate: TextView = view.findViewById(R.id.tv_selected_date)

        flIcSelectTerm.setOnClickListener {
            showDatePickerDialog(tvSelectedDate)
        }

        val doneBtn: FrameLayout = view.findViewById(R.id.fl_ic_teambuild_writing_done_btn)

        // "작성완료" 버튼에 클릭 리스너 설정
        doneBtn.setOnClickListener {

            showDoneDialog()
        }

        // spinner 데이터
        val data = listOf("2","3","4","5","6")

        // Spinner initialization
        val spinner: Spinner = view.findViewById(R.id.spinner_profile_select_member_num)

        // Adapter creation and data setting
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        return view
    }

    private fun showDatePickerDialog(tvSelectedDate: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                tvSelectedDate.text = selectedDate

            },
            year, month, day
        )

        val calendarMax = Calendar.getInstance()
        calendarMax.add(Calendar.DAY_OF_MONTH, 90)
        datePickerDialog.datePicker.maxDate = calendarMax.timeInMillis

        datePickerDialog.show()
    }

    private fun showDoneDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("") // 대화 상자의 제목 설정
        builder.setMessage("한 번 작성한 글은 수정이 불가능합니다. 작성 완료하시겠습니까?") // 메시지 설정

        builder.setPositiveButton("네") { dialog, which ->
            // "저장" 버튼 클릭 처리 (저장 동작)
            sendTeamCreateRequest() // 팀 생성 요청 보내는 함수 호출
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

    private fun sendTeamCreateRequest() {
        // UI 요소에서 값을 가져오기
        val teamTitle = view?.findViewById<EditText>(R.id.tv_teambuild_writing_title)?.text.toString()
        val teamInfoMessage = view?.findViewById<EditText>(R.id.tv_teambuild_writing_content)?.text.toString()
        val maxMember = view?.findViewById<Spinner>(R.id.spinner_profile_select_member_num)?.selectedItem.toString().toInt()
        val recruitmentEndDate = view?.findViewById<TextView>(R.id.tv_selected_date)?.text.toString()

        // 실제로는 subjectId 값을 어떻게 가져올지에 대한 로직을 여기에 추가하세요.
        val subjectId = 3 // 예시로 1로 설정해 두었습니다. 실제로 사용하는 값으로 교체하세요.

        // TeamCreateRequest 객체 생성
        val teamCreateRequest = TeamCreateRequest(
            subjectId = subjectId,
            teamTitle = teamTitle,
            recruitmentEndDate = recruitmentEndDate,
            maxMember = maxMember,
            teamInfoMessage = teamInfoMessage
        )

        // Retrofit을 사용하여 서버로 팀 생성 요청 보내기
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitImpl.authenticatedRetrofit.create(TeamService::class.java).createTeam(teamCreateRequest)

                // 로그 찍기
                Log.d("TeamBuildWriting", "Request: $teamCreateRequest")
                Log.d("TeamBuildWriting", "Response: ${response.raw()}")

                if (response.isSuccessful) {
                    // 성공적으로 처리된 경우의 로직을 여기에 추가하세요.
                    Log.d("TeamBuildWriting", "팀 생성 성공")
                } else {
                    // 에러가 발생한 경우의 로직을 여기에 추가하세요.
                    Log.e("TeamBuildWriting", "팀 생성 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                // 예외가 발생한 경우의 로직을 여기에 추가하세요.
                Log.e("TeamBuildWriting", "팀 생성 실패: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}