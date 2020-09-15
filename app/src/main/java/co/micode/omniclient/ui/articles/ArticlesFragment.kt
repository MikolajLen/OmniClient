package co.micode.omniclient.ui.articles

import android.os.Bundle
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.ui.list.BaseListFragment
import javax.inject.Inject

class ArticlesFragment : BaseListFragment<ArticleEntity, ArticlesHolder>() {

    @Inject
    lateinit var articlesAdapter: ArticlesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.articlesData.observe(viewLifecycleOwner, {
            showOrHideList(it)
            articlesAdapter.setItems(it)
        })
    }

    override fun getBaseAdapter() = articlesAdapter
}