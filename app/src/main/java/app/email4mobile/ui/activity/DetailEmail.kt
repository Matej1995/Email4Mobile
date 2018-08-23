package app.email4mobile.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import app.email4mobile.R
import app.email4mobile.viewmodel.DetailUserViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.android.synthetic.main.activity_detail_event.*
import kotlinx.android.synthetic.main.email_detail.*

class DetailEmail: AppCompatActivity() {

    private var viewModel: DetailUserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.email_detail)

        viewModel = ViewModelProviders.of(this).get(DetailUserViewModel::class.java)
        populateUserDetail()
        close.setOnClickListener{

            finish()

        }



    }

    private fun populateUserDetail() {
        viewModel?.getUserDetail(this.intent.getStringExtra("username"))?.observe(this,
                Observer { userDetail ->

                    if (userDetail != null) {

                        login.text = userDetail.login
                        location.text = userDetail.location

                        Glide.with(this).load(userDetail.avatar_url).preload()
                        Glide.with(this).load(userDetail.avatar_url).into(profile_image)


                    } else {
                        Toast.makeText(this, "Failed Fetching Remote Data", Toast.LENGTH_LONG).show()
                        finish()
                    }
                })


    }


}