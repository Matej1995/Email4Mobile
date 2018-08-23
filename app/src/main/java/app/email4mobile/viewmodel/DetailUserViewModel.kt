package app.email4mobile.viewmodel

import android.arch.lifecycle.LiveData
import app.email4mobile.model.UserDetail

class DetailUserViewModel : BaseViewModel() {

    fun getUserDetail(login: String): LiveData<UserDetail>? {
        return repository.getUserDetail(login)

    }
}