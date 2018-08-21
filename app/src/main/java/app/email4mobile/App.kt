package app.email4mobile

import android.app.Application
import android.arch.persistence.room.Room
import app.email4mobile.di.component.AppComponent
import app.email4mobile.di.component.DaggerAppComponent
import app.email4mobile.di.module.AppModule
import app.email4mobile.di.module.NetModule
import app.email4mobile.di.module.RoomModule
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }



    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initializeDagger()

    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule())
                .netModule(NetModule(this)).build()

    }

}