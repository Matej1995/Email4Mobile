package app.email4mobile.data.email

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import app.email4mobile.data.email.entity.CalendarEvent
import io.reactivex.Flowable

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(event: CalendarEvent)

    @Query(RoomContract.select_from_event)
    fun selectAll(): Flowable<List<CalendarEvent>>
}