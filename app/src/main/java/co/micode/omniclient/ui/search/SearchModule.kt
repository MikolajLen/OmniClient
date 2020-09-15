package co.micode.omniclient.ui.search

import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun provideArticlesTopicsAdapter(fragment: SearchFragment) = ArticlesTopicsAdapter(fragment)
}