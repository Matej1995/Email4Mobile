package app.email4mobile.viewmodel

import android.app.AlertDialog
import android.content.Context
import app.email4mobile.data.email.entity.CalendarEvent

class CalendarFragmentViewModel : BaseViewModel() {

    val itemsForImportant = arrayOf<CharSequence>("Delete")
    var listOfEvent = mutableListOf<CalendarEvent>()

    fun getListOfEvents():MutableList<CalendarEvent> = listOfEvent

    fun addEventToDatabase(event: CalendarEvent){
        room.eventDao().insertAll(event)
    }

    fun selectAllEvents(): MutableList<CalendarEvent> = room.eventDao().selectAll()

    fun selectEventCloseInfo(context: Context ) {
        val dialog = AlertDialog.Builder(context).setTitle("Select: ").setItems(itemsForImportant,
                { dialog, which ->
                })
        dialog.show()
    }
}