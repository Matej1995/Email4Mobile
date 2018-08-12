package app.email4mobile.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.email4mobile.R
import kotlinx.android.synthetic.main.activity_add_event.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.CompoundButton
import android.widget.Toast
import app.email4mobile.data.EventsDataBase
import app.email4mobile.model.CalendarEvent


class AddEvent : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setUpToolbar()
        setUpActualTimeDate()
        showAndHideTextViewHours()




    }

    private fun saveDataToDb(eventDatabase: EventsDataBase){
        val addEvent = CalendarEvent(1, add_tittle.text.toString(), add_location.text.toString(), startPickerDate.text.toString() + " " + startPickerHours.text.toString(), endTimePickerDate.text.toString() + " "+ endTimePickerHours.text.toString(), repeat.text.toString(), alert.text.toString(), important.text.toString(), 123456 )
        eventDatabase.eventsDataDao().insert(addEvent)
        Toast.makeText(applicationContext, startPickerDate.text.toString(), Toast.LENGTH_LONG).show()
    }

    private fun setUpToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpActualTimeDate() {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date())
        startPickerDate.text = currentDate
        startPickerHours.text = (Date().hours.toString() + ":" + Date().minutes.toString())
        endTimePickerDate.text = currentDate
        endTimePickerHours.text = (Date().hours.plus(1).toString() + ":" + Date().minutes.toString())
    }

    fun clickOnDate(view: View) {
        val cal = Calendar.getInstance()

        val eventDatabase =
                Room.databaseBuilder(applicationContext, EventsDataBase::class.java, "EventDatabase")
                        .allowMainThreadQueries()
                        .build()

        val itemsForRepeat = arrayOf<CharSequence>("Only One", "Every Day", "One per year")

        val itemsForAllert = arrayOf<CharSequence>("30 minut", "60 minut", "1 day")

        val itemsForImportant = arrayOf<CharSequence>("Personal", "Work")

        when (view.id) {
            R.id.startPickerDate -> getTimeDate(cal, startPickerDate)

            R.id.startPickerHours -> getTimeHours(cal, startPickerHours)

            R.id.endTimePickerDate -> getTimeDate(cal, endTimePickerDate)

            R.id.endTimePickerHours -> getTimeHours(cal, endTimePickerHours)

            R.id.repeat -> selectEventCloseInfo(repeatText, itemsForRepeat)

            R.id.alert -> selectEventCloseInfo(alertTitle, itemsForAllert)

            R.id.important -> selectEventCloseInfo(importantTitle, itemsForImportant)

            R.id.saveDataToDb -> saveDataToDb(eventDatabase)
        }


    }


    private fun selectEventCloseInfo(textView: TextView, items: Array<CharSequence>) {
        val dialog = AlertDialog.Builder(this).setTitle("Select: ").setItems(items,
                { dialog, which ->
                    textView.text = items[which]
                })
        dialog.show()
    }

    fun getTimeDate(cal: Calendar, textView: TextView) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat)
            textView.text = sdf.format(cal.time)

        }
        DatePickerDialog(this@AddEvent, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()

    }

    fun getTimeHours(cal: Calendar, textView: TextView) {

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(this@AddEvent, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    fun showAndHideTextViewHours() {
        switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                startPickerHours.visibility = View.INVISIBLE
                endTimePickerHours.visibility = View.INVISIBLE
            } else {
                startPickerHours.visibility = View.VISIBLE
                endTimePickerHours.visibility = View.VISIBLE
            }
        })
    }





}

