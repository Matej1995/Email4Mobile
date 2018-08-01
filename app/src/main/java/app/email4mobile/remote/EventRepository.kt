package app.email4mobile.remote

import app.email4mobile.entity.CalendarEvent
import io.reactivex.Flowable

class EventRepository(private val mLocationDataSource: IEventDataSource): IEventDataSource {
    override val allEvents: Flowable<List<CalendarEvent>>
        get() = mLocationDataSource.allEvents

    override fun getEventById(eventId: Int): Flowable<CalendarEvent> {
        return mLocationDataSource.getEventById(eventId)
    }

    override fun insertEvent(vararg event: CalendarEvent) {
        mLocationDataSource.insertEvent(*event)
    }

    override fun deleteEvent(event: CalendarEvent) {
        mLocationDataSource.deleteEvent(event)
    }

    override fun deleteAllUser() {
        mLocationDataSource.deleteAllUser()
    }


}