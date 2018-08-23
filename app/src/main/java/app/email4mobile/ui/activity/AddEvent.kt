package app.email4mobile.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.email4mobile.R
import kotlinx.android.synthetic.main.activity_add_event.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import app.email4mobile.data.email.entity.CalendarEvent
import app.email4mobile.viewmodel.AddEventViewModel
import app.email4mobile.viewmodel.CalendarFragmentViewModel
import petrov.kristiyan.colorpicker.ColorPicker
import kotlin.collections.ArrayList
import android.graphics.Color.parseColor
import kotlinx.android.synthetic.main.activity_add_event.view.*


class AddEvent : AppCompatActivity() {


    private var viewModel: CalendarFragmentViewModel? = null
    private var viewModelForAddEvent: AddEventViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setUpToolbar()
        setUpActualTimeDate()


        viewModel = ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java)
        viewModelForAddEvent = ViewModelProviders.of(this).get(AddEventViewModel::class.java)


    }

    private fun saveDataToDb() {
        val eventSize = viewModelForAddEvent?.selectAllEvents()
        val addEvent = CalendarEvent(eventSize?.size?.toLong()?.inc(), add_tittle.text.toString(), add_location.text.toString(), startPickerDate.text.toString() + " " + startPickerHours.text.toString(), endTimePickerDate.text.toString() + " " + endTimePickerHours.text.toString(),
                repeat.text.toString(), alert.text.toString(), important.text.toString(), colorPuf.hint.toString().toInt())

        val intent = intent
        intent.putExtra("event", addEvent)
        setResult(1, intent)
        Toast.makeText(this, "Event byl přidán", Toast.LENGTH_LONG).show()
        finish()
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

        val itemsForRepeat = arrayOf<CharSequence>("Only One", "Every Day", "One per year")

        val itemsForAllert = arrayOf<CharSequence>("30 minut", "60 minut", "1 day")

        val itemsForImportant = arrayOf<CharSequence>("Personal", "Work")

        when (view.id) {
            R.id.startPickerDate -> getTimeDate(cal, startPickerDate, true)

            R.id.startPickerHours -> getTimeHours(cal, startPickerHours, true)

            R.id.endTimePickerDate -> getTimeDate(cal, endTimePickerDate, false)

            R.id.endTimePickerHours -> getTimeHours(cal, endTimePickerHours, false)

            R.id.repeat -> selectEventCloseInfo(repeatText, itemsForRepeat)

            R.id.alert -> selectEventCloseInfo(alertTitle, itemsForAllert)

            R.id.important -> selectEventCloseInfo(importantTitle, itemsForImportant)

            R.id.saveDataToDb -> saveDataToDb()

            R.id.colorPuf ->showDialog()
        }
    }


    private fun selectEventCloseInfo(textView: TextView, items: Array<CharSequence>) {
        val dialog = AlertDialog.Builder(this).setTitle("Select: ").setItems(items,
                { dialog, which ->
                    textView.text = items[which]
                })
        dialog.show()
    }

    fun getTimeDate(cal: Calendar, textView: TextView, flag: Boolean) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat)
            textView.text = sdf.format(cal.time)
            if (flag) {
                endTimePickerDate.text = startPickerDate.text
            }
        }
        DatePickerDialog(this@AddEvent, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun getTimeHours(cal: Calendar, textView: TextView, flag: Boolean) {

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
            if (flag) {
                endTimePickerHours.text = startPickerHours.text
            }
        }
        TimePickerDialog(this@AddEvent, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

    private fun showDialog() {
        val colorPicker = ColorPicker(this)
        val listOfColor = ArrayList<String>()
        listOfColor.add("#258174")
        listOfColor.add("#3C8D2F")
        listOfColor.add("#20724f")
        listOfColor.add("#6a3ab2")

        listOfColor.add("#FFAA1D")
        listOfColor.add("#FFEF00")
        listOfColor.add("#7B3F00")
        listOfColor.add("#02075D")


        colorPicker.setColors(listOfColor)
                .setColumns(4)
                .setRoundColorButton(true)
                .setTitle("Vyberte barvu: ")
                .setOnChooseColorListener(object : ColorPicker.OnFastChooseColorListener, ColorPicker.OnChooseColorListener {


                    override fun setOnFastChooseColorListener(position: Int, color: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChooseColor(position: Int, color: Int) {
                        colorTitle.setBackgroundColor(color)
                        colorPuf.hint = color.toString()
                        val x = 4
                    }



                    override fun onCancel() {
                        colorPicker.dismissDialog()
                    }

                })

                .show()

    }

}

