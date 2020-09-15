package co.micode.omniclient.ui.list

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.micode.omniclient.utils.SingleLiveEvent

abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val items = arrayListOf<T>()
    protected val navigateToDetailsEvents = SingleLiveEvent<String>()
    val navigateActions: LiveData<String?> = navigateToDetailsEvents

    override fun getItemCount() = items.size

    fun setItems(newItems: List<T>) {
        val diffCallback = createDiffUtill(items, newItems)
        val result = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

    abstract fun createDiffUtill(oldList: List<T>, newList: List<T>): DiffUtil.Callback
}