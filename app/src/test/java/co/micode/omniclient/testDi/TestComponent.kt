package co.micode.omniclient.testDi

import co.micode.omniclient.di.ActivitiesFragmentModule
import co.micode.omniclient.di.OmniClientApplication
import co.micode.omniclient.ui.articles.ArticlesFragmentTest
import co.micode.omniclient.ui.topics.TopicsFragmentTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, ActivitiesFragmentModule::class, TestModule::class])
interface TestComponent {
    fun inject(application: OmniClientApplication)
    fun inject(test: ArticlesFragmentTest)
    fun inject(test: TopicsFragmentTest)
}