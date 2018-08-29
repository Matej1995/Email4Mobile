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
import app.email4mobile.model.User
import app.email4mobile.ui.activity.SendEmailActivity
import app.email4mobile.viewmodel.EmailViewModel
import kotlinx.android.synthetic.main.fragment_email.*

/**
 * A simple [Fragment] subclass.
 *
 */
class EmailFragment : Fragment() {

    private val viewModel: EmailViewModel? by lazy {
        ViewModelProviders.of(this).get(EmailViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        fetchData()
        swipeRefreshLayout.setOnRefreshListener { fetchData() }
        openToSendNewEmail()
    }

    private fun fetchData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel?.getUsers()?.observe(this,
                Observer { userList ->
                    if (userList != null) {
                        viewModel?.addUserToLocal(userList)
                        rv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                        rv.adapter = EmailAdapter(userList as ArrayList<User>)
                    } else {
                        callDataFromLocal()
                        Toast.makeText(context, getString(R.string.FailedFetchData), Toast.LENGTH_LONG).show()
                    }
                    swipeRefreshLayout.isRefreshing = false
                })
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    fun callDataFromLocal() {
        viewModel?.getUserFromLocal()?.observe(this, Observer { userList ->
            rv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            rv.adapter = EmailAdapter(userList as ArrayList<User>)
        })
    }

    fun openToSendNewEmail() {
        fab.setOnClickListener {
            val intent = Intent(context, SendEmailActivity::class.java)
            startActivity(intent)
        }
    }
}
