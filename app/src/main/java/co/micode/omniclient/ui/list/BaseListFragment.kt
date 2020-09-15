package co.micode.omniclient.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.micode.omniclient.R
import co.micode.omniclient.ui.details.DetailsFragment
import co.micode.omniclient.ui.search.SearchViewModel
import co.micode.omniclient.ui.search.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list_layout.*
import javax.inject.Inject

abstract class BaseListFragment<T, VH : RecyclerView.ViewHolder> : DaggerFragment() {

    @Inject
    lateinit var factory: SearchViewModelFactory

    protected val viewModel by lazy {
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
            adapter = getBaseAdapter()
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        getBaseAdapter().navigateActions.observe(viewLifecycleOwner, {
            val bundle = bundleOf(DetailsFragment.DETAILS_TAG to it)
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
        })
    }

    protected fun showOrHideList(list: List<T>) {
        empty_placeholder.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        items_list.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
    }

    abstract fun getBaseAdapter(): BaseListAdapter<T, VH>
}