import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R

class SubjectAdapter(private val context: Context, private val subjectList: List<String>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_rcv_evalution_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjectList[position]
        holder.bind(subject)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_evaluation_subject)

        fun bind(subject: String) {
            textView.text = subject
        }
    }
}
