package app.email4mobile.ui.fragments


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import app.email4mobile.R
import app.email4mobile.data.email.entity.CalendarEvent
import app.email4mobile.ui.activity.AddEventActivity
import app.email4mobile.ui.activity.DetailEventActivity
import app.email4mobile.utils.Notifications
import app.email4mobile.viewmodel.CalendarFragmentViewModel
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 *
 */
class CalendarFragment : Fragment(), MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewClickListener {


    var eventList: MutableList<WeekViewEvent> = ArrayList()

    var list = mutableListOf<CalendarEvent>()

    private val viewModelCalendar: CalendarFragmentViewModel? by lazy {
        ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java)
    }

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
        event.color = resources.getColor(R.color.colorAccent)
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
        event.color = resources.getColor(R.color.colorAccent)
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
        event.color = resources.getColor(R.color.defaultColor)
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
        event.color = resources.getColor(R.color.defaultColor)
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

    override fun onEventClick(event: WeekViewEvent?, eventRect: RectF?) {
        val intent = Intent(context, DetailEventActivity::class.java)
        intent.putExtra("name", event?.name)
        startActivity(intent)
    }

    override fun onEventLongPress(event: WeekViewEvent?, eventRect: RectF?) {
        context?.let { viewModelCalendar?.selectEventCloseInfo(it) }
    }

    override fun onEmptyViewClicked(time: Calendar?) {
        Toast.makeText(context, getString(R.string.ClickOnEmtyEvent), Toast.LENGTH_LONG).show()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    private fun clickFloatButton(cont: Context) {
        val x = Notifications()
        // x.createNotification(context!!)
        x.scheduleNotification(cont, x.getNotification(cont, "5 second delay"), 5000)
        floatingActionButton.setOnClickListener {
            val intent = Intent(context, AddEventActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null) {
            val eventData = data.getParcelableExtra<CalendarEvent>("event")
            viewModelCalendar?.addEventToDatabase(eventData)

            val ft = fragmentManager!!.beginTransaction()
            ft.remove(CalendarFragment())
                    .replace(R.id.main_frame, CalendarFragment())
                    .commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpDraversNav()
        weekView.setMonthChangeListener(this)
        weekView.setOnEventClickListener(this)
        weekView.eventLongPressListener = this
        weekView.emptyViewClickListener = this
        loadEventsFromDB()
        clickFloatButton(context!!)
    }


    fun loadEventsFromDB() {
        list = viewModelCalendar?.selectAllEvents()!!
        list.forEach {
            val startTimeEvent = fromStringToDate(it.startTime)
            val endTimeEvent = fromStringToDate(it.endTime)
            addActualEvent(startTimeEvent, endTimeEvent, it.id!!, it.color)
        }
        weekView.notifyDatasetChanged()
    }

    fun addActualEvent(startTimeEvent: Long, endTimeEvent: Long, id: Long, color: Int) {
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
        event.color = color
        eventList.add(event)
    }

    protected fun getEventTitle(time: Calendar): String {
        return String.format(getString(R.string.formatEventTitle), time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH))
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

