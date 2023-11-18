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

        return rootView
    }
}
