package co.micode.omniclient.testDi

import co.micode.omniclient.ui.search.SearchViewModelFactory
import co.micode.omniclient.utils.ImageLoader
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModule {

    @Provides
    @Singleton
    fun provideSearchViewModelFactory(): SearchViewModelFactory = mock()

    @Provides
    @Singleton
    fun provideImageLoader(): ImageLoader = mock()
}