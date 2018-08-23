package app.email4mobile.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.email4mobile.R
import app.email4mobile.viewmodel.EmailViewModel
import app.email4mobile.viewmodel.SendEmailViewModel

class SendEmail : AppCompatActivity(){

    private var viewModel: SendEmailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_email)
        viewModel = ViewModelProviders.of(this).get(SendEmailViewModel::class.java)
       // viewModel!!.sendEmail("Ahoj", "test")
    }


}