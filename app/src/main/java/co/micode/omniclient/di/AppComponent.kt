package co.micode.omniclient.di

import co.micode.omniclient.api.ApiModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, ApiModule::class, PicassoModule::class, ActivitiesFragmentModule::class])
interface AppComponent {

    fun inject(application: OmniClientApplication)
}