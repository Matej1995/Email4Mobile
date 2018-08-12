package app.email4mobile.di.module

import android.content.Context
import app.email4mobile.data.email.RoomData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    @Provides
    @Singleton
fun provideRoomCurrencyDataSource(context: Context) = RoomData.buildPersistentCurrency(context)
}