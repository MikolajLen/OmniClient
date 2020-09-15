package co.micode.omniclient.ui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.micode.omniclient.R
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.utils.ImageLoader
import co.micode.omniclient.utils.SingleLiveEvent
import kotlinx.android.synthetic.main.item_article.view.*
import javax.inject.Inject

class ArticlesAdapter
@Inject
constructor(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<ArticlesHolder>() {

    private val items = arrayListOf<ArticleEntity>()
    private val navigateToDetailsEvents = SingleLiveEvent<String>()
    val navigateActions: LiveData<String?> = navigateToDetailsEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticlesHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false),
        imageLoader
    )

    override fun onBindViewHolder(holder: ArticlesHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            navigateToDetailsEvents.postValue(items[position].text)
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<ArticleEntity>) {
        val diffCallback = ArticlesDiffCallback(items, newItems)
        val result = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }
}

class ArticlesHolder(view: View, private val imageLoader: ImageLoader) :
    RecyclerView.ViewHolder(view) {

    private val textView = view.article_title
    private val imageView = view.article_image

    fun bind(entity: ArticleEntity) {
        textView.text = entity.title
        imageLoader.loadImage(entity.imageId, imageView)
    }
}

class ArticlesDiffCallback(
    private val oldItems: List<ArticleEntity>,
    private val newItems: List<ArticleEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].title == newItems[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}
