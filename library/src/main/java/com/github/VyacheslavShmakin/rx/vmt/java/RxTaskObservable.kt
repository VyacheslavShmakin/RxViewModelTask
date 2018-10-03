package com.github.VyacheslavShmakin.rx.vmt.java

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import com.github.VyacheslavShmakin.rx.vmt.RxBaseTask
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * RxTaskObservable
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
class RxTaskObservable<T> private constructor(
        owner: ViewModelStoreOwner,
        id: String,
        private val observable: Observable<T>) : RxBaseTask<T>(owner, id) {

    companion object {
        @JvmStatic
        fun <T> create(
                owner: ViewModelStoreOwner,
                id: String,
                observable: Observable<T>): RxTaskObservable<T> {

            return RxTaskObservable(owner, id, observable)
        }
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun init(observer: Observer<T>, collectAll: Boolean = false) {
        mModel.init(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun restart(observer: Observer<T>, collectAll: Boolean = false) {
        mModel.restart(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner?, observer: Observer<T>) {
        if (owner == null) {
            return
        }
        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer.onError(data.error!!)
                data.result != null -> observer.onNext(data.result)
                else -> observer.onComplete()
            }
        })
    }
}

