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



        return view
    }

}