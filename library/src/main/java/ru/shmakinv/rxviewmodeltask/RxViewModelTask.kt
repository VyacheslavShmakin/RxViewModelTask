package ru.shmakinv.rxviewmodeltask

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.ViewModelStoreOwner
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver

/**
 * RxViewModelTask
 *
 * @author Vyacheslav Shmakin
 * @version 30.09.2018
 */
class RxViewModelTask<T> private constructor(private val mTask: RxViewModel<T>) {

    companion object {
        fun <T> create(
                owner: ViewModelStoreOwner,
                id: String): RxViewModelTask<T> {

            return RxViewModelTask(getViewModel(owner, id))
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> getViewModel(owner: ViewModelStoreOwner?, id: String): RxViewModel<T> {
            return when (owner) {
                is Fragment -> ViewModelProviders.of(owner).get(id, RxViewModel::class.java) as RxViewModel<T>
                is FragmentActivity -> ViewModelProviders.of(owner).get(id, RxViewModel::class.java) as RxViewModel<T>
                null -> throw NullPointerException("ViewModelStoreOwner should be defined!")
                else -> throw IllegalArgumentException("Owner should be defined as Fragment or FragmentActivity")
            }
        }
    }
    /* Observable region start*/
    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun init(owner: LifecycleOwner, observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mTask.init(observable, collectAll)
        observeLiveData(owner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun restart(owner: LifecycleOwner, observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mTask.restart(observable, collectAll)
        observeLiveData(owner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner, observer: Observer<T>?) {
        mTask.mLiveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer?.onError(data.error!!)
                data.result != null -> observer?.onNext(data.result)
                else -> observer?.onComplete()
            }
        })
    }
    /* Observable region end*/
    /* Single region start*/
    fun init(owner: LifecycleOwner, single: Single<T>, observer: SingleObserver<T>) {
        mTask.init(single)
        observeLiveData(owner, observer)
    }

    fun restart(owner: LifecycleOwner, single: Single<T>, observer: SingleObserver<T>) {
        mTask.restart(single)
        observeLiveData(owner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner, observer: SingleObserver<T>?) {
        mTask.mLiveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer?.onError(data.error!!)
                data.result != null -> observer?.onSuccess(data.result)
            }
        })
    }
    /* Single region end*/
}

