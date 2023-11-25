package com.ssu.bilda.presentation.teambuild

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
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

                val selectedDate = "$selectedYear.${selectedMonth + 1}.$selectedDay"
                tvSelectedDate.text = selectedDate
            },
            year, month, day
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }

    private fun showDoneDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("") // 대화 상자의 제목 설정
        builder.setMessage("한 번 작성한 글은 수정이 불가능합니다. 작성 완료하시겠습니까?") // 메시지 설정

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
