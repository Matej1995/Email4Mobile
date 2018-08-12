package app.email4mobile.repository

import android.arch.lifecycle.MutableLiveData
import app.email4mobile.model.Email

interface IEmailRepository {

    fun getEmail(): MutableLiveData<List<Email>>

    fun getEmailDetail(login: String): MutableLiveData<Email>

    fun addEmailToLocal(listUser : List<Email>)

    fun getEmailLocal(): MutableLiveData<List<Email>>

    fun addNewEmail(name: String, email: String)

    fun deleteEmailByID(id: Int)

}