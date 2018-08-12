package app.email4mobile.data

import android.arch.persistence.room.*
import app.email4mobile.model.CalendarEvent
import io.reactivex.Flowable

@Dao
interface EventsDataDao {


    @Query("SELECT * FROM eventsData WHERE id = :id")
    fun getEventById(id: String): Flowable<CalendarEvent>

    @Query("SELECT * FROM eventsData ")
    fun getEvents(): MutableList<CalendarEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: CalendarEvent)


    @Query("DELETE FROM eventsData WHERE id = :userId")
    fun deleteEvent(userId: Int)

    @Query("DELETE FROM eventsData")
    fun deleteAll()
}



