package app.email4mobile.repository

import android.arch.lifecycle.MutableLiveData
import app.email4mobile.model.User
import app.email4mobile.model.UserDetail

interface IEmailRepository {

    fun getUser(): MutableLiveData<List<User>>

    fun getUserDetail(login: String): MutableLiveData<UserDetail>

    fun addUserToLocal(listUser : List<User>)

    fun getUserLocal(): MutableLiveData<List<User>>

    fun addNewEmail(name: String, email: String)

    fun deleteEmailByID(id: Int)

}