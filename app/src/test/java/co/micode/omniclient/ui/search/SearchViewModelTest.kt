package co.micode.omniclient.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.micode.omniclient.data.NewsDataSource
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.data.entities.TopicEntity
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val topics = listOf(mock<TopicEntity>())
    val articles = listOf(mock<ArticleEntity>())
    val dataSource = mock<NewsDataSource>()

    @ExperimentalCoroutinesApi
    @Test
    fun `should post error when exception was thrown`() = runBlockingTest {
        //given
        whenever(dataSource.fetchNews(any())).thenThrow(RuntimeException("message"))
        val underTest = SearchViewModel(dataSource)

        //when
        underTest.search("test")

        //then
        assertThat(underTest.errorData.value).isEqualTo("message")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should post articles`() = runBlockingTest {
        //given
        whenever(dataSource.fetchNews(any())).thenReturn(articles to topics)
        val underTest = SearchViewModel(dataSource)

        //when
        underTest.search("test")

        //then
        assertThat(underTest.articlesData.value).isEqualTo(articles)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should post topics`() = runBlockingTest {
        //given
        whenever(dataSource.fetchNews(any())).thenReturn(articles to topics)
        val underTest = SearchViewModel(dataSource)

        //when
        underTest.search("test")

        //then
        assertThat(underTest.topicsData.value).isEqualTo(topics)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should fetch data from endpoint`() = runBlockingTest {
        //given
        val underTest = SearchViewModel(dataSource)

        //when
        underTest.search("test")

        //then
        verify(dataSource).fetchNews("test")
    }
}