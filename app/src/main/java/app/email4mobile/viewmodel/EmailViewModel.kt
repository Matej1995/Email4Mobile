package app.email4mobile.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import app.email4mobile.model.User
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class EmailViewModel : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var liveData: LiveData<List<User>>? = null

    fun getUsers(): MutableLiveData<List<User>> {
        return repository.getUser()
    }

    fun sendEmail(name: String, email: String) {
        return repository.addNewEmail(name, email)
    }

    fun addUserToLocal(email: List<User>) {
        Completable.fromAction { repository.addUserToLocal(email) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        Timber.i("DataSource has been populated")
                    }

                    override fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                        Timber.i("DataSource hasn't been populated")
                    }
                })
    }

    fun getUserFromLocal(): LiveData<List<User>>? {
        if (liveData == null) {
            liveData = MutableLiveData<List<User>>()
            liveData = repository.getUserLocal()
        }
        return liveData
    }
}