package app.email4mobile.remote

import app.email4mobile.entity.CalendarEvent
import io.reactivex.Flowable

interface IEventDataSource {
    val allEvents: Flowable<List<CalendarEvent>>

    fun getEventById(eventId: Int): Flowable<CalendarEvent>

    fun insertEvent(vararg event:CalendarEvent)

    fun deleteEvent(event:CalendarEvent)

    fun deleteAllUser()

}