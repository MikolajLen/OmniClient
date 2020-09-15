package co.micode.omniclient.di

import co.micode.omniclient.MainActivity
import co.micode.omniclient.ui.articles.ArticlesFragment
import co.micode.omniclient.ui.search.SearchFragment
import co.micode.omniclient.ui.search.SearchModule
import co.micode.omniclient.ui.topics.TopicsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeTopicsFragment(): TopicsFragment

    @ContributesAndroidInjector
    abstract fun contributeArticlesFragment(): ArticlesFragment

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchFragment(): SearchFragment
}