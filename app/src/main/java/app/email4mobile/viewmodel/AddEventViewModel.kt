package app.email4mobile.viewmodel

import app.email4mobile.data.email.entity.CalendarEvent

class AddEventViewModel : BaseViewModel() {


    fun addEventToDatabase(event: CalendarEvent){
        room.eventDao().insertAll(event)
    }


}