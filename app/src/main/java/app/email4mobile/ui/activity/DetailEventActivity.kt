package app.email4mobile.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.email4mobile.R
import kotlinx.android.synthetic.main.activity_detail_event.*


class DetailEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        setSupportActionBar(mainToolbar)
        name.text = intent.getStringExtra("name")
    }
}
