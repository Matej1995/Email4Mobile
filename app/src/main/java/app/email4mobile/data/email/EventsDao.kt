package app.email4mobile.data.email

import android.arch.persistence.room.*
import app.email4mobile.data.email.entity.CalendarEvent

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(event: CalendarEvent)

    @Query(RoomContract.select_from_event)
    fun selectAll(): MutableList<CalendarEvent>

    @Delete
    fun deleteEvent(event: CalendarEvent)
}