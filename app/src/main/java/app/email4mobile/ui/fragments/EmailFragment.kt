

package app.email4mobile.ui.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import app.email4mobile.R
import app.email4mobile.adapter.EmailAdapter
import app.email4mobile.model.Email
import app.email4mobile.ui.activity.AddEvent
import app.email4mobile.ui.activity.SendEmail
import app.email4mobile.viewmodel.EmailViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_email.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class EmailFragment : Fragment() {

    private var viewModel: EmailViewModel? = null



    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProviders.of(this).get(EmailViewModel::class.java)
        featchData()
        swipeRefreshLayout.setOnRefreshListener { featchData() }
        openToSendNewEmail()
    }

    private fun featchData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel?.getEmails()?.observe(this,
                Observer { emailist ->
                    if (emailist != null) {
                        viewModel?.addUserToLocal(emailist)

                        rv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                       rv.adapter = EmailAdapter(emailist as ArrayList<Email>)

                    } else {
                        callDataFromLocal()
                        Toast.makeText(context, "Failed Fetching Remote Data", Toast.LENGTH_LONG).show()
                    }
                    swipeRefreshLayout.isRefreshing = false
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }


    fun callDataFromLocal(){
        viewModel?.getUserFromLocal()?.observe(this, Observer { userList ->
            rv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            rv.adapter = EmailAdapter(userList as ArrayList<Email>)
        })
    }

    fun openToSendNewEmail(){
        fab.setOnClickListener {
            val intent = Intent(context, SendEmail::class.java)
            startActivity(intent)
        }
    }


}
