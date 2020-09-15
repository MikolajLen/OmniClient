package co.micode.omniclient.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class PicassoModule {

    @Provides
    fun providePicasso() = Picasso.get()
}