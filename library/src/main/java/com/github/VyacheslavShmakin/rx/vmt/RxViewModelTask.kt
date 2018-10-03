package com.github.VyacheslavShmakin.rx.vmt

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import io.reactivex.*

/**
 * RxViewModelTask
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
class RxViewModelTask<T> private constructor(
        owner: ViewModelStoreOwner,
        id: String) : RxBaseTask<T>(owner, id) {

    companion object {
        fun <T> create(
                owner: ViewModelStoreOwner,
                id: String): RxViewModelTask<T> {

            return RxViewModelTask(owner, id)
        }
    }
    /* Observable region start*/
    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun init(observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.init(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun restart(observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
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
    /* Observable region end*/

    /* Single region start*/
    fun init(single: Single<T>, observer: SingleObserver<T>) {
        mModel.init(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(single: Single<T>, observer: SingleObserver<T>) {
        mModel.restart(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner?, observer: SingleObserver<T>) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer.onError(data.error!!)
                data.result != null -> observer.onSuccess(data.result)
            }
        })
    }
    /* Single region end*/

    /* Flowable region start*/
    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun init(flowable: Flowable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.init(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun restart(flowable: Flowable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.restart(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }
    /* Flowable region end*/

    /* Maybe region start*/
    fun init(maybe: Maybe<T>, observer: MaybeObserver<T>) {
        mModel.init(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(maybe: Maybe<T>, observer: MaybeObserver<T>) {
        mModel.restart(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner?, observer: MaybeObserver<T>) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer.onError(data.error!!)
                data.result != null -> observer.onSuccess(data.result)
                else -> observer.onComplete()
            }
        })
    }
    /* Maybe region end*/

    /* Completable region start*/
    fun init(completable: Completable, observer: CompletableObserver) {
        mModel.init(completable)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(completable: Completable, observer: CompletableObserver) {
        mModel.restart(completable)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner?, observer: CompletableObserver) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer.onError(data.error!!)
                else -> observer.onComplete()
            }
        })
    }
    /* Completable region end*/
}

