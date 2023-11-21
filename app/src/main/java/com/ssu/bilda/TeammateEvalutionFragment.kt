import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ssu.bilda.R
import github.hongbeomi.dividerseekbar.DividerSeekBar

class TeammateEvalutionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_teammate_evalution, container, false)

        // Initialize DividerSeekBar
        val dividerSeekBar = DividerSeekBar(requireContext()).apply {
            max = 100
            // ... (Set other properties)
        }

        // Set additional properties after initializing the DividerSeekBar
        dividerSeekBar.setTextLocationMode(DividerSeekBar.TEXT_LOCATION_BOTTOM)

        // Find the FrameLayout container in the layout
//        val frameLayoutContainer: FrameLayout = rootView.findViewById(R.id.fl_seek_bar_container)

        // Add DividerSeekBar to FrameLayout
//        frameLayoutContainer.addView(dividerSeekBar)

        // "저장" 버튼을 위한 FrameLayout 찾기
        val saveButton: FrameLayout = rootView.findViewById(R.id.fl_blue_save_btn)

        // "저장" 버튼에 클릭 리스너 설정
        saveButton.setOnClickListener {
            // "저장" 버튼을 클릭했을 때 대화 상자 표시
            showSaveDialog()
        }

        return rootView
    }

    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("저장") // 대화 상자의 제목 설정
        builder.setMessage("이 팀원과 다시 한 번 팀플 하시겠습니까?") // 메시지 설정

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
