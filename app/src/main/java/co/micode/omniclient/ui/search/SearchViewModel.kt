package co.micode.omniclient.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.micode.omniclient.data.NewsDataSource
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.data.entities.TopicEntity
import co.micode.omniclient.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel
@Inject
constructor(private val newsDataSource: NewsDataSource) : ViewModel() {

    private val articlesSource = MutableLiveData<List<ArticleEntity>>()
    val articlesData: LiveData<List<ArticleEntity>> = articlesSource

    private val topicsSource = MutableLiveData<List<TopicEntity>>()
    val topicsData: LiveData<List<TopicEntity>> = topicsSource

    private val errorSource = SingleLiveEvent<String>()
    val errorData: LiveData<String?> = errorSource

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val (articles, topics) = newsDataSource.fetchNews(query)
                articlesSource.postValue(articles)
                topicsSource.postValue(topics)
            } catch (e: Exception) {
                errorSource.postValue(e.message)
            }
        }
    }
}