package co.micode.omniclient.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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

class TopicsFragment : DaggerFragment() {

    @Inject
    lateinit var topicsAdapter: TopicsAdapter

    @Inject
    lateinit var factory: SearchViewModelFactory

    val viewModel by lazy {
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
            adapter = topicsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        topicsAdapter.navigateActions.observe(viewLifecycleOwner, {
            val bundle = bundleOf(DetailsFragment.DETAILS_TAG to it)
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
        })
        viewModel.topicsData.observe(viewLifecycleOwner, {
            empty_placeholder.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            items_list.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            topicsAdapter.setItems(it)
        })
    }
}