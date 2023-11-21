import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R

class SubjectAdapter(private val context: Context, private val subjectList: List<String>) :
// RecyclerView.Adapter 클래스 상속 받음 & subjetViewHolder -> 제네릭
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var onItemClick: ((View) -> Unit)? = null

    fun setOnItemClickListener(listener: (View) -> Unit) {
        onItemClick = listener
    }

    // 새로운 아이템 뷰 위한 뷰홀더 객체 생성, 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_rcv_evalution_subject, parent, false)
        // 만들어진 뷰 객체 -> SubjectViewHolder의 인스턴스로 감싸서 반환
        return SubjectViewHolder(view)
    }

    // 특정 위치의 데이터를 뷰 홀더에 바인딩
    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjectList[position]
        holder.bind(subject)
    }

    // 데이터의 총 아이템 수 반환
    override fun getItemCount(): Int {
        return subjectList.size
    }

    //아이템의 각 뷰 홀더, RecyclerView.ViewHolder 클래스 상속 받음
    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_evaluation_subject)
        private val rightArrowLayout: FrameLayout =
            itemView.findViewById(R.id.fl_ic_gray_right_arrow)

        fun bind(subject: String) {
            textView.text = subject

            rightArrowLayout.setOnClickListener {
                onItemClick?.invoke(rightArrowLayout)
            }
        }
    }
}
