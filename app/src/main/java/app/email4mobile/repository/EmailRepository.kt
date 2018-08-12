package app.email4mobile.repository

import android.arch.lifecycle.MutableLiveData
import app.email4mobile.data.email.RoomData
import app.email4mobile.data.email.entity.EmailEntity
import app.email4mobile.model.Email
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


    //REMOTE CALL
    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun addNewEmail(name: String, email: String) {
        remoteData.netCallSendNewEmail(name, email)
    }


    override fun deleteEmailByID(id: Int) {
        remoteData.netCallDeleteEmailByID(id)
    }

    override fun getEmail(): MutableLiveData<List<Email>> {
        val mutableLiveData = MutableLiveData<List<Email>>()
        val disposable = remoteData.netCallGetEmail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ emailList ->
                    mutableLiveData.value = emailList
                }, { error ->
                    Timber.i("error: " + error.message)
                    mutableLiveData.value = null
                })
       allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun getEmailDetail(login: String): MutableLiveData<Email> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addEmailToLocal(listUser: List<Email>) {
        val userList = ArrayList<EmailEntity>()
        listUser.forEach {
            userList.add(EmailEntity(it.id,
                    it.id,
                    it.name,
                    it.email
            ))
        }
        roomData.userDao().insertAll(userList)

    }

    override fun getEmailLocal(): MutableLiveData<List<Email>> {
        val mutableLiveData = MutableLiveData<List<Email>>()
        val disposable = roomData.userDao().selectAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    mutableLiveData.value = transform(userList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }



    private fun transform(users: List<EmailEntity>): ArrayList<Email> {
        val userList = ArrayList<Email>()
        users.forEach {
            userList.add(
                    Email(
                            it.iid,
                            it.name,
                            it.email
                    ))
        }
        return userList

    }
}