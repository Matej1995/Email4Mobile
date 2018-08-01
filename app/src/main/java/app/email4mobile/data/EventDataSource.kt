package app.email4mobile.data

import app.email4mobile.entity.CalendarEvent
import app.email4mobile.remote.IEventDataSource
import io.reactivex.Flowable

class EventDataSource(private val eventDao : EventsDataDao): IEventDataSource {


    override val allEvents: Flowable<List<CalendarEvent>>
        get() = eventDao.allEvent

    override fun getEventById(eventId: Int): Flowable<CalendarEvent> {
        return eventDao.getEventByID(eventId)
    }

    override fun insertEvent(vararg event: CalendarEvent) {
        eventDao.insert(*event)
    }

    override fun deleteEvent(event: CalendarEvent) {
        eventDao.deleteEvent(event)
    }

    override fun deleteAllUser() {
        eventDao.deleteAll()
    }


}