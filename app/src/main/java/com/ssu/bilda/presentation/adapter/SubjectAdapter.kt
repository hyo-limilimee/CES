import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.bilda.R
import com.ssu.bilda.data.common.Subject
import com.ssu.bilda.data.common.SubjectWithTeamStatus

class SubjectAdapter(private var subjectList: List<SubjectWithTeamStatus>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var itemClickListener: ((View) -> Unit)? = null

    fun setOnItemClickListener(listener: (View) -> Unit) {
        itemClickListener = listener
    }

    fun updateData(newList: List<SubjectWithTeamStatus>) {
        subjectList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_evalution_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subjectWithTeamStatus = subjectList[position]
        holder.bind(subjectWithTeamStatus)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_evaluation_subject)

        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(it)
            }
        }

        fun bind(subjectWithTeamStatus: SubjectWithTeamStatus) {
            titleTextView.text = subjectWithTeamStatus.subject.title
            // You may need to access other properties from SubjectWithTeamStatus as needed
        }
    }
}
