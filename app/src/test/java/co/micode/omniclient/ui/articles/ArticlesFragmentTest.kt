package co.micode.omniclient.ui.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.micode.omniclient.R
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.data.entities.TopicEntity
import co.micode.omniclient.di.OmniClientApplication
import co.micode.omniclient.testDi.DaggerTestComponent
import co.micode.omniclient.ui.search.SearchViewModel
import co.micode.omniclient.ui.search.SearchViewModelFactory
import co.micode.omniclient.utils.SingleLiveEvent
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ArticlesFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var articlesAdapter: ArticlesAdapter

    @Inject
    lateinit var factory: SearchViewModelFactory

    private val articlesSource = MutableLiveData<List<ArticleEntity>>()
    private val topicsSource = MutableLiveData<List<TopicEntity>>()
    private val errorSource = SingleLiveEvent<String>()

    private val viewModel: SearchViewModel = mock {
        on(it.articlesData).thenReturn(articlesSource)
        on(it.topicsData).thenReturn(topicsSource)
        on(it.errorData).thenReturn(errorSource)
    }

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<OmniClientApplication>()
        val component = DaggerTestComponent.create()
        component.inject(app)
        component.inject(this)
        whenever(factory.create(SearchViewModel::class.java)).thenReturn(viewModel)
    }

    @Test
    fun `should show articles when list is not empty`() {
        //given
        launchFragmentInContainer<ArticlesFragment>(themeResId = R.style.AppTheme)
        val testArticle = ArticleEntity("test title", "id", "text")

        //when
        articlesSource.postValue(listOf(testArticle))

        //then
        onView(withText("test title")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun `should show placeholder when list is empty`() {
        //given
        launchFragmentInContainer<ArticlesFragment>()

        //when
        articlesSource.postValue(emptyList())

        //then
        onView(withText(R.string.no_results_has_been_found)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun `should navigate on item click`() {
        //given
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)
        val scenario = launchFragmentInContainer<ArticlesFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        val testArticle = ArticleEntity("test title", "id", "text")
        articlesSource.postValue(listOf(testArticle))

        //when
        onView(withText("test title")).perform(ViewActions.click())

        //then
        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailsFragment)
    }
}