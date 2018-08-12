package app.email4mobile.di.module

import android.content.Context
import app.email4mobile.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App){

    @Provides
    @Singleton
    fun provideContext(): Context = app

}