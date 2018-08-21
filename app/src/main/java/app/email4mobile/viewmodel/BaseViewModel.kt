package app.email4mobile.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.RoomDatabase
import app.email4mobile.App
import app.email4mobile.data.email.RoomData
import app.email4mobile.repository.EmailRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var repository: EmailRepository

    @Inject
    lateinit var room: RoomData

    init {
        App.appComponent.inject(this)
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in repository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()

    }

}
