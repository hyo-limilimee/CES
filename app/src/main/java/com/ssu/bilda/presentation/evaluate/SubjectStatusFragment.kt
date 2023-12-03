package com.ssu.bilda.presentation.evaluate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.remote.UserSharedPreferences
import com.ssu.bilda.presentation.adapter.TeammateNameAdapter

class SubjectStatusFragment : Fragment() {

    companion object {
        fun newInstance(title: String): SubjectStatusFragment {
            val fragment = SubjectStatusFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment layout inflate
        val view = inflater.inflate(R.layout.fragment_subject_status, container, false)


        // 제목 설정
        val title = arguments?.getString("title", "") ?: ""

        // Set the title to the TextView
        val tvSubjectName: TextView = view.findViewById(R.id.tv_subject_name)
        tvSubjectName.text = title

        // Set userName to the TextView
        val tvEvaluationName: TextView = view.findViewById(R.id.tv_evaluation_name)
        val userName = UserSharedPreferences.getUserName(requireContext())
        tvEvaluationName.text = userName

        // recyclerview 더미 데이터
        val teammateNameList = listOf("팀원 1", "팀원 2", "팀원 3")

        // adapter 생성, 설정
        val adapter = TeammateNameAdapter(requireContext(), teammateNameList)

        // RecyclerView를 초기화, adapter 설정
        val rvTeammateNameList: RecyclerView = view.findViewById(R.id.rv_teammate_name_list)
        rvTeammateNameList.layoutManager = LinearLayoutManager(requireContext())
        rvTeammateNameList.adapter = adapter

        adapter.setOnItemClickListener { view ->
            // 아이템이 클릭되었을 때 수행할 동작 구현
            replaceFragment(TeammateEvalutionFragment())
        }
        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}
