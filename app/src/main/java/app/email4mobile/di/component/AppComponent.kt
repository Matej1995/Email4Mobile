package app.email4mobile.di.component

import app.email4mobile.di.module.AppModule
import app.email4mobile.di.module.NetModule
import app.email4mobile.di.module.RoomModule
import app.email4mobile.viewmodel.BaseViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = arrayOf(AppModule::class, RoomModule::class, NetModule::class))
@Singleton
interface AppComponent {

    fun inject(baseViewModel: BaseViewModel)
}