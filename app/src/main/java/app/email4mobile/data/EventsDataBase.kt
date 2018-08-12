package app.email4mobile.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import app.email4mobile.model.CalendarEvent

@Database(entities = [(CalendarEvent::class)], version = 2, exportSchema = false)
abstract class EventsDataBase : RoomDatabase() {

    abstract fun eventsDataDao(): EventsDataDao

    companion object {

        @Volatile private var INSTANCE: EventsDataBase? = null

        fun getInstance(context: Context): EventsDataBase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        EventsDataBase::class.java, "Event.db")
                        .build()
    }

}