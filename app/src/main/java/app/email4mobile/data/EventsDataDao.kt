package app.email4mobile.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import app.email4mobile.entity.CalendarEvent
import io.reactivex.Flowable

@Dao
interface EventsDataDao {

    @get:Query("SELECT * from eventsData")
    val allEvent: Flowable<List<CalendarEvent>>

    @Query("SELECT * from eventsData WHERE id=:eventID")
    fun getEventByID(eventID: Int): Flowable<CalendarEvent>

    @Insert(onConflict = REPLACE)
    fun insert(vararg event: CalendarEvent)


    @Query("DELETE from eventsData")
    fun deleteEvent(events: CalendarEvent)

    @Delete
    fun deleteAll()
}



