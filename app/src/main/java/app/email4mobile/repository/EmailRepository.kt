package app.email4mobile.repository

import android.arch.lifecycle.MutableLiveData
import app.email4mobile.data.email.RoomData
import app.email4mobile.data.email.entity.EmailEntity
import app.email4mobile.data.email.entity.UserEntity
import app.email4mobile.model.Email
import app.email4mobile.model.User
import app.email4mobile.model.UserDetail
import app.email4mobile.remote.RemoteData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailRepository
@Inject constructor(
        private val roomData: RoomData,
        private val remoteData: RemoteData
) : IEmailRepository {
    override fun getUser(): MutableLiveData<List<User>> {
        val mutableLiveData = MutableLiveData<List<User>>()
        val disposable = remoteData.netCallGetEmail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userlist ->
                    mutableLiveData.value = userlist
                }, { error ->
                    Timber.i("error: " + error.message)
                    mutableLiveData.value = null
                })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun getUserDetail(login: String): MutableLiveData<UserDetail> {
        val mutableLiveData = MutableLiveData<UserDetail>()
        val disposable = remoteData.netCallGetEmailDetail(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userDetail ->
                    mutableLiveData.value = userDetail
                }, { error ->
                    Timber.i("error: " + error.message)
                    mutableLiveData.value = null
                })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun addUserToLocal(listUser: List<User>) {
        val userList = ArrayList<UserEntity>()
        listUser.forEach {
            userList.add(UserEntity(it.id,
                    it.login,
                    it.id,
                    it.node_id,
                    it.avatar_url,
                    it.gravatar_id,
                    it.url,
                    it.html_url,
                    it.followers_url,
                    it.following_url,
                    it.gists_url,
                    it.starred_url,
                    it.subscriptions_url,
                    it.organizations_url,
                    it.repos_url,
                    it.events_url,
                    it.received_events_url,
                    it.type,
                    it.site_admin
            ))
        }
        roomData.usersDao().insertAll(userList)
    }

    override fun getUserLocal(): MutableLiveData<List<User>> {
        val mutableLiveData = MutableLiveData<List<User>>()
        val disposable = roomData.usersDao().selectAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    mutableLiveData.value = transform(userList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }


    //REMOTE CALL
    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun addNewEmail(name: String, email: String) {
        remoteData.netCallSendNewEmail(name, email)
    }


    override fun deleteEmailByID(id: Int) {
        remoteData.netCallDeleteEmailByID(id)
    }





    private fun transform(users: List<UserEntity>): ArrayList<User> {
        val userList = ArrayList<User>()
        users.forEach {
            userList.add(
                    User(it.login,
                            it.id,
                            it.node_id,
                            it.avatar_url,
                            it.gravatar_id,
                            it.url,
                            it.html_url,
                            it.followers_url,
                            it.following_url,
                            it.gists_url,
                            it.starred_url,
                            it.subscriptions_url,
                            it.organizations_url,
                            it.repos_url,
                            it.events_url,
                            it.received_events_url,
                            it.type,
                            it.site_admin
                    ))
        }
        return userList
    }
}