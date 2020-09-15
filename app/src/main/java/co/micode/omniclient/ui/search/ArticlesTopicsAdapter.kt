package co.micode.omniclient.ui.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.micode.omniclient.ui.articles.ArticlesFragment
import co.micode.omniclient.ui.topics.TopicsFragment
import javax.inject.Inject

class ArticlesTopicsAdapter
@Inject
constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = TABS_COUNT

    override fun createFragment(position: Int) =
        when (position) {
            0 -> ArticlesFragment()
            1 -> TopicsFragment()
            else -> throw Exception("Incorrect position $position")
        }

    companion object {
        private const val TABS_COUNT = 2
    }
}