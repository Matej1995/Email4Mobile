package app.email4mobile.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.widget.TextView
import app.email4mobile.data.email.entity.CalendarEvent
import app.email4mobile.data.email.entity.UserEntity
import app.email4mobile.model.CalendarEventModel
import app.email4mobile.model.User
import com.alamkanak.weekview.WeekViewEvent
import io.reactivex.Flowable

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