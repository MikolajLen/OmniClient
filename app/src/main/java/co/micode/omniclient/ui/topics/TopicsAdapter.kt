package co.micode.omniclient.ui.topics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.micode.omniclient.R
import co.micode.omniclient.data.entities.TopicEntity
import co.micode.omniclient.ui.list.BaseListAdapter
import kotlinx.android.synthetic.main.item_topic.view.*
import javax.inject.Inject

class TopicsAdapter
@Inject
constructor() : BaseListAdapter<TopicEntity, TopicsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TopicsHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
    )

    override fun onBindViewHolder(holder: TopicsHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            navigateToDetailsEvents.postValue(items[position].type)
        }
    }

    override fun createDiffUtill(
        oldList: List<TopicEntity>,
        newList: List<TopicEntity>
    ) = TopicsDiffCallback(oldList, newList)
}

class TopicsHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textView = view.topic_textview

    fun bind(entity: TopicEntity) {
        textView.text = entity.title
    }
}

class TopicsDiffCallback(
    private val oldItems: List<TopicEntity>,
    private val newItems: List<TopicEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].title == newItems[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}

