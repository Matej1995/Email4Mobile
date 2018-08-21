package app.email4mobile.viewmodel

import app.email4mobile.data.email.entity.CalendarEvent

class CalendarFragmentViewModel : BaseViewModel() {

    var listOfEvent = mutableListOf<CalendarEvent>()


    fun getListOfEvents():MutableList<CalendarEvent> = listOfEvent


    fun addEventToDatabase(event: CalendarEvent){
        room.eventDao().insertAll(event)
    }




}