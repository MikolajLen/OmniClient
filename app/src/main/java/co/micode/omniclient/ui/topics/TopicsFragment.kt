package co.micode.omniclient.ui.topics

import android.os.Bundle
import co.micode.omniclient.data.entities.TopicEntity
import co.micode.omniclient.ui.list.BaseListAdapter
import co.micode.omniclient.ui.list.BaseListFragment
import javax.inject.Inject

class TopicsFragment : BaseListFragment<TopicEntity, TopicsHolder>() {

    @Inject
    lateinit var topicsAdapter: TopicsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.topicsData.observe(viewLifecycleOwner, {
            showOrHideList(it)
            topicsAdapter.setItems(it)
        })
    }

    override fun getBaseAdapter() = topicsAdapter
}