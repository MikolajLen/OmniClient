package co.micode.omniclient.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.micode.omniclient.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject
import javax.inject.Provider

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var articlesTopicsAdapter: Provider<ArticlesTopicsAdapter>

    @Inject
    lateinit var factory: SearchViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view_pager.adapter = articlesTopicsAdapter.get()
        TabLayoutMediator(tabs, view_pager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.articles)
                1 -> tab.text = getString(R.string.topics)
                else -> throw IllegalArgumentException("position $position not found")
            }
        }.attach()
        viewModel.errorData.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view_pager, R.string.error_message, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let(viewModel::search)
                    return true
                }

                override fun onQueryTextChange(newText: String?) = true
            })
        }
        super.onCreateOptionsMenu(menu, menuInflater)
    }
}