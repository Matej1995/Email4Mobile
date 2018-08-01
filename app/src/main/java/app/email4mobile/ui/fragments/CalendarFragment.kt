package app.email4mobile.ui.fragments


import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.email4mobile.R
import app.email4mobile.ui.activity.AddEvent
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CalendarFragment : Fragment(), MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener {


    override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent> {

        val events = mutableListOf<WeekViewEvent>()

        val startTime = Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 3)
        startTime.set(Calendar.MINUTE, 35)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        val endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR, 1)
        endTime.set(Calendar.MONTH, newMonth - 1)
        endTime.set(Calendar.MINUTE, 24)
        val event = WeekViewEvent(1, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.defaultColor)
        events.add(event)

        return events

    }


    override fun onEventClick(event: WeekViewEvent?, eventRect: RectF?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEventLongPress(event: WeekViewEvent?, eventRect: RectF?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)

    }

    override fun onStart() {
        super.onStart()
        clickFloatButton()
        setUpDraversNav()

        // Set an action when any event is clicked.
        weekView.setOnEventClickListener(this)

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        weekView.setMonthChangeListener(this)

        // Set long press listener for events.
        weekView.setEventLongPressListener(this)

    }



    fun addEvent() {

        val timeZone = TimeZone.getTimeZone("Europe/Prague")
        val events = mutableListOf<EventDay>()

        val calendar = java.util.Calendar.getInstance(timeZone)

        events.add(EventDay(calendar, R.drawable.baseline_email_black_18dp))

        val xa = events
        val x = 5
    }

    private fun clickFloatButton() {
        floatingActionButton.setOnClickListener {
            val intent = Intent(context, AddEvent::class.java)
            startActivity(intent)
        }
    }

    protected fun getEventTitle(time: Calendar): String {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH))
    }
    fun setUpDraversNav(){
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.calendar_7 ->  {
                    weekView.numberOfVisibleDays = 7
                    true
                }
                R.id.calendar_1 ->  {
                    weekView.numberOfVisibleDays = 1
                    true
                }
                R.id.calendar_3 ->  {
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
