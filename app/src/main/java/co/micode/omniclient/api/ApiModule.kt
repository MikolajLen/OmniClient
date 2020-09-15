package co.micode.omniclient.api

import co.micode.omniclient.BuildConfig
import co.micode.omniclient.api.configuration.ApiConfiguration
import co.micode.omniclient.api.services.OmniNewsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideApiConfiguration() =
        ApiConfiguration().provideApiConfiguration("https://omni-content.omni.news")

    @Provides
    fun provideNewsService(retrofit: Retrofit) = retrofit.create(OmniNewsService::class.java)
}