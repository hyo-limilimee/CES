package com.ssu.bilda.presentation.evaluate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.presentation.adapter.SubjectAdapter
import com.ssu.bilda.presentation.mypage.MyInfoFragment
import com.ssu.bilda.presentation.teambuild.TeamBuildWritingFragment

class ProjectStatusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment layout inflate
        val view = inflater.inflate(R.layout.fragment_project_status, container, false)

        // recyclerview 더미 데이터
        val subjectList = listOf("과목 1", "과목 2", "과목 3")

        // adapter 생성, 설정
        val adapter = SubjectAdapter(requireContext(), subjectList)

        // RecyclerView를 초기화, adapter 설정
        val rvSubjectList: RecyclerView = view.findViewById(R.id.rv_subject_list)
        rvSubjectList.layoutManager = LinearLayoutManager(requireContext())
        rvSubjectList.adapter = adapter

        // SubjectAdapter의 클릭 리스너 설정
        adapter.setOnItemClickListener { view ->
            // 아이템이 클릭되었을 때 수행할 동작 구현
            replaceFragment(SubjectStatusFragment())
        }

        // 임시 버튼 클릭 리스너 설정
        val settingButton: FrameLayout = view.findViewById(R.id.fl_ic_setting_btn)

        settingButton.setOnClickListener { view ->
            replaceFragment(TeamBuildWritingFragment())
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, fragment)
            .commit()
    }
}