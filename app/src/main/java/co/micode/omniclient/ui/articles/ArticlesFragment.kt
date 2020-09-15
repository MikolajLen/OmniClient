package co.micode.omniclient.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.micode.omniclient.R
import co.micode.omniclient.ui.DetailsFragment
import co.micode.omniclient.ui.search.SearchViewModel
import co.micode.omniclient.ui.search.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list_layout.*
import javax.inject.Inject

class ArticlesFragment : DaggerFragment() {

    @Inject
    lateinit var articlesAdapter: ArticlesAdapter

    @Inject
    lateinit var factory: SearchViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_list_layout, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articlesAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        articlesAdapter.navigateActions.observe(viewLifecycleOwner, {
            val bundle = bundleOf(DetailsFragment.DETAILS_TAG to it)
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
        })
        viewModel.articlesData.observe(viewLifecycleOwner, {
            empty_placeholder.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            items_list.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            articlesAdapter.setItems(it)
        })
    }
}