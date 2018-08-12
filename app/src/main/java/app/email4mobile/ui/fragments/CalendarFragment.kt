package app.email4mobile.ui.fragments


import android.arch.persistence.room.Room
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import app.email4mobile.R
import app.email4mobile.data.EventsDataBase
import app.email4mobile.model.CalendarEvent
import app.email4mobile.ui.activity.AddEvent
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
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

    private var eventList: MutableList<CalendarEvent> = ArrayList()
    private var composibleDisposable: CompositeDisposable? = null

    private var eventDatabase: EventsDataBase? = null

    override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent>? {

        val events = mutableListOf<WeekViewEvent>()


       /*  val listEventDatabase: MutableList<CalendarEvent> = eventDatabase!!.eventsDataDao().getEvents()
        listEventDatabase.forEach {
            val startTime = fromStringToDate(it.startTime)
            val endTime = fromStringToDate(it.endTime)
            val eventForCalendar = WeekViewEvent(1, "Ahoj", startTimeEvent(startTime), startTimeEvent(endTime))
            events.add(eventForCalendar)
        }
        listEventDatabase.size
        events.size
        val t = 5
        */
        return events

    }

    fun startTimeEvent(event: Calendar): Calendar? {
        val startTime = Calendar.getInstance()
        startTime.set(Calendar.DAY_OF_WEEK, event.get(Calendar.DAY_OF_WEEK))
        startTime.set(Calendar.MINUTE, event.get(Calendar.MINUTE))
        startTime.set(Calendar.MONTH, event.get(Calendar.MONTH))
        startTime.set(Calendar.YEAR, event.get(Calendar.YEAR))
        return startTime
    }


    fun fromStringToDate(date: String): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val data = date
        val x = 4
        cal.time = sdf.parse(date)// all done
        return cal
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventDatabase = context?.let {
            Room.databaseBuilder(it, EventsDataBase::class.java, "EventDatabase")
                    .allowMainThreadQueries()
                    .build()
        }


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
        // setUpDraversNav()

        weekView.setMonthChangeListener(this)

        weekView.setOnEventClickListener(this)


        weekView.setEventLongPressListener(this)


        clickFloatButton(eventDatabase!!)

    }


    private fun deleteAllEvents() {
        eventDatabase!!.eventsDataDao().deleteAll()
    }


    private fun clickFloatButton(eventDatabase: EventsDataBase) {
        floatingActionButton.setOnClickListener {
            val intent = Intent(context, AddEvent::class.java)
            startActivity(intent)
        }
    }

    protected fun getEventTitle(time: Calendar): String {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH))
    }

    /*  fun setUpDraversNav() {
          nav_view.setNavigationItemSelectedListener {
              when (it.itemId) {
                  R.id.calendar_7 -> {
                      weekView.numberOfVisibleDays = 7
                      deleteAllEvents() // vymaže celou databázi pak dát pryč
                      true
                  }
                  R.id.calendar_1 -> {
                      weekView.numberOfVisibleDays = 1
                      deleteAllEvents() // vymaže celou databázi pak dát pryč
                      true
                  }
                  R.id.calendar_3 -> {
                      weekView.numberOfVisibleDays = 3
                      deleteAllEvents() // vymaže celou databázi pak dát pryč
                      true
                  }
                  else -> {
                      false
                  }
              }
          }
      }
      */


}

