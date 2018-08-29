package app.email4mobile.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import app.email4mobile.R
import app.email4mobile.ui.fragments.CalendarFragment
import app.email4mobile.ui.fragments.EmailFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(EmailFragment())
        setUpNav()
    }

    fun setUpNav() {
        main_nav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_email -> {
                    val emailFragment = EmailFragment()
                    setFragment(emailFragment)
                    true
                }
                R.id.nav_calendar -> {
                    val calendarFragment = CalendarFragment()
                    setFragment(calendarFragment)
                    true
                }
                R.id.nav_account -> {

                    true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        })
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}



