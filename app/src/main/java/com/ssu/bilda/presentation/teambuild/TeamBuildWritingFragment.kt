package com.ssu.bilda.presentation.teambuild

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
}
