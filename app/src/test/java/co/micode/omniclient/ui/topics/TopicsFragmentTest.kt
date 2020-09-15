package co.micode.omniclient.ui.topics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.micode.omniclient.R
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.data.entities.TopicEntity
import co.micode.omniclient.di.OmniClientApplication
import co.micode.omniclient.testDi.DaggerTestComponent
import co.micode.omniclient.ui.search.SearchViewModel
import co.micode.omniclient.ui.search.SearchViewModelFactory
import co.micode.omniclient.utils.SingleLiveEvent
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class TopicsFragmentTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var topicsAdapter: TopicsAdapter

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
    fun `should show topics when list is not empty`() {
        //given
        launchFragmentInContainer<TopicsFragment>(themeResId = R.style.AppTheme)
        val testArticle = TopicEntity("test title", "type")

        //when
        topicsSource.postValue(listOf(testArticle))

        //then
        onView(ViewMatchers.withText("test title")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `should show placeholder when list is empty`() {
        //given
        launchFragmentInContainer<TopicsFragment>()

        //when
        topicsSource.postValue(emptyList())

        //thjen
        onView(ViewMatchers.withText(R.string.no_results_has_been_found))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `should navigate on item click`() {
        //given
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)
        val scenario = launchFragmentInContainer<TopicsFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        val testArticle = TopicEntity("test title", "type")
        topicsSource.postValue(listOf(testArticle))

        //when
        onView(ViewMatchers.withText("test title")).perform(click())

        //then
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailsFragment)
    }
}