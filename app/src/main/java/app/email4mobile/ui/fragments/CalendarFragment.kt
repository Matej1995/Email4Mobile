package app.email4mobile.ui.fragments


import android.app.Activity.RESULT_CANCELED
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import app.email4mobile.R
import app.email4mobile.data.email.entity.CalendarEvent
import app.email4mobile.ui.activity.AddEvent
import app.email4mobile.ui.activity.DetailEvent
import app.email4mobile.viewmodel.CalendarFragmentViewModel
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 *
 */
class CalendarFragment : Fragment(), MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener {

    var eventList: MutableList<WeekViewEvent> = ArrayList()

    var list = mutableListOf<CalendarEvent>()

    private var viewModelCalendar: CalendarFragmentViewModel? = null

    override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent>? {


        val events = mutableListOf<WeekViewEvent>()



        var startTime = Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 3)
        startTime.set(Calendar.MINUTE, 30)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        var endTime = startTime.clone() as Calendar
        endTime.set(Calendar.HOUR_OF_DAY, 4)
        endTime.set(Calendar.MINUTE, 30)
        endTime.set(Calendar.MONTH, newMonth - 1)
        var event = WeekViewEvent(10, getEventTitle(startTime), startTime, endTime)
        event.setColor(resources.getColor(R.color.colorAccent))
        events.add(event)

        startTime = Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 4)
        startTime.set(Calendar.MINUTE, 20)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime = startTime.clone() as Calendar
        endTime.set(Calendar.HOUR_OF_DAY, 5)
        endTime.set(Calendar.MINUTE, 0)
        event = WeekViewEvent(10, getEventTitle(startTime), startTime, endTime)
        event.setColor(resources.getColor(R.color.colorAccent))
        events.add(event)

        startTime = Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 5)
        startTime.set(Calendar.MINUTE, 30)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 2)
        endTime.set(Calendar.MONTH, newMonth - 1)
        event = WeekViewEvent(2, getEventTitle(startTime), startTime, endTime)
        event.setColor(resources.getColor(R.color.defaultColor))
        events.add(event)

        startTime = Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 5)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        startTime.add(Calendar.DATE, 1)
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        endTime.set(Calendar.MONTH, newMonth - 1)
        event = WeekViewEvent(3, getEventTitle(startTime), startTime, endTime)
        event.setColor(resources.getColor(R.color.defaultColor))
        events.add(event)


        for (item in eventList) {
            if (eventMatches(item, newYear, newMonth)) {
                    events.add(item)
            }
        }

        return events

    }
    private fun eventMatches(event: WeekViewEvent, year: Int, month: Int): Boolean {
        return event.startTime.get(Calendar.YEAR) == year && event.startTime.get(Calendar.MONTH) == month - 1 || event.endTime.get(Calendar.YEAR) == year && event.endTime.get(Calendar.MONTH) == month - 1
    }


    fun fromStringToDate(date: String): Long {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val date = sdf.parse(date)
        return date.time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelCalendar = ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java)

    }

    override fun onEventClick(event: WeekViewEvent?, eventRect: RectF?) {
        val intent = Intent(context, DetailEvent::class.java)
        intent.putExtra("name", event?.name)
        startActivityForResult(intent, 2)
    }

    override fun onEventLongPress(event: WeekViewEvent?, eventRect: RectF?) {
        context?.let { viewModelCalendar?.selectEventCloseInfo(it) }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)

    }

    override fun onStart() {
        super.onStart()
        setUpDraversNav()

        weekView.setMonthChangeListener(this)

        weekView.setOnEventClickListener(this)

        weekView.setEventLongPressListener(this)

        loadEventsFromDB()


        clickFloatButton()
    }


    private fun clickFloatButton() {
        floatingActionButton.setOnClickListener {
            val intent = Intent(context, AddEvent::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && data != null) {
            val eventData = data?.getParcelableExtra<CalendarEvent>("event")
            viewModelCalendar?.addEventToDatabase(eventData)

            val ft = fragmentManager!!.beginTransaction()
            ft.remove(CalendarFragment())
                    .replace(R.id.main_frame, CalendarFragment())
                    .commit()
        }
        if (resultCode == RESULT_CANCELED) {
            val ft = fragmentManager!!.beginTransaction()
            ft.remove(CalendarFragment())
                    .replace(R.id.main_frame, CalendarFragment())
                    .commit()
            return
        }

    }

    fun loadEventsFromDB(){
        list = viewModelCalendar?.selectAllEvents()!!
        list?.forEach {
            val startTimeEvent = fromStringToDate(it.startTime)
            val endTimeEvent = fromStringToDate(it.endTime)
            addActualEvent(startTimeEvent, endTimeEvent, it.id!!, it.color)
        }
        weekView.notifyDatasetChanged()
    }

    fun addActualEvent(startTimeEvent: Long,endTimeEvent: Long, id: Long, color: Int ){
        val calendarStart = Calendar.getInstance()
        calendarStart.timeInMillis = startTimeEvent
        calendarStart.get(Calendar.YEAR)
        calendarStart.get(Calendar.MONTH)
        calendarStart.get(Calendar.DAY_OF_MONTH)

        val calendarEnd = Calendar.getInstance()
        calendarEnd.timeInMillis = endTimeEvent
        calendarEnd.get(Calendar.YEAR)
        calendarEnd.get(Calendar.MONTH)
        calendarEnd.get(Calendar.DAY_OF_MONTH)
        var event = WeekViewEvent(id, getEventTitle(calendarStart), calendarStart, calendarEnd)
        event?.color = color
        eventList.add(event)
    }

    protected fun getEventTitle(time: Calendar): String {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH))
    }

      fun setUpDraversNav() {
          calendarFragmentNav.setNavigationItemSelectedListener {
              when (it.itemId) {
                  R.id.calendar_7 -> {
                      weekView.numberOfVisibleDays = 7
                      true
                  }
                  R.id.calendar_1 -> {
                      weekView.numberOfVisibleDays = 1
                      true
                  }
                  R.id.calendar_3 -> {
                      weekView.numberOfVisibleDays = 3
                      true
                  }
                  else -> {
                      false
                  }
              }
          }
      }

}

