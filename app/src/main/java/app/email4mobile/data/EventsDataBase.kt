package app.email4mobile.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import app.email4mobile.entity.CalendarEvent

@Database(entities = [(CalendarEvent::class)], version = 1)
abstract class EventsDataBase : RoomDatabase() {

    abstract fun eventsDataDao(): EventsDataDao

    companion object {
        private var INSTANCE: EventsDataBase? = null

        fun getInstance(context: Context): EventsDataBase? {
            if (INSTANCE == null) {
                synchronized(EventsDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            EventsDataBase::class.java, "weather.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}