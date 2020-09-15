package co.micode.omniclient.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.micode.omniclient.data.NewsDataSource
import javax.inject.Inject

class SearchViewModelFactory
@Inject
constructor(private val newsDataSource: NewsDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            if (isAssignableFrom(SearchViewModel::class.java)) {
                SearchViewModel(newsDataSource) as T
            } else throw IllegalArgumentException("Unknown viewModel class")
        }

}