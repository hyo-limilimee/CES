import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R

class ProjectStatusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 인플레이트합니다.
        val view = inflater.inflate(R.layout.fragment_project_status, container, false)

        // RecyclerView에 들어갈 샘플 데이터
        val subjectList = listOf("과목 1", "과목 2", "과목 3")

        // 어댑터를 생성하고 설정합니다.
        val adapter = SubjectAdapter(requireContext(), subjectList)

        // RecyclerView를 찾아 초기화하고 어댑터를 설정합니다.
        val rvSubjectList: RecyclerView = view.findViewById(R.id.rv_subject_list)
        rvSubjectList.layoutManager = LinearLayoutManager(requireContext())
        rvSubjectList.adapter = adapter

        return view
    }
}
