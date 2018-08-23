package app.email4mobile.ui.activity

import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.email4mobile.R
import kotlinx.android.synthetic.main.activity_detail_event.*


class DetailEvent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        setSupportActionBar(mainToolbar)
        val intent = intent

        name.text = intent.getStringExtra("name")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack()
        else
            super.onBackPressed();

    }
}
