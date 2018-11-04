package ru.shmakinv.androidarchitecturetest.util.observable

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import com.github.VyacheslavShmakin.rx.vmt.RxBaseTask
import com.github.VyacheslavShmakin.rx.vmt.RxResult
import io.reactivex.*

/**
 * RxViewModelTask
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
open class RxViewModelTask private constructor(
        owner: ViewModelStoreOwner,
        id: String) : RxBaseTask(owner, id) {

    companion object {
        @JvmStatic
        fun create(
                owner: ViewModelStoreOwner,
                id: String): RxViewModelTask {

            return RxViewModelTask(owner, id)
        }
    }
    /* Observable region start*/
    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun <T> init(observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.init(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun <T> restart(observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.restart(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun <T> observeLiveData(owner: LifecycleOwner?, observer: Observer<T>) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer<RxResult<T>> {
            when {
                it!!.error != null -> observer.onError(it.error!!)
                it.result != null -> observer.onNext(it.result)
                else -> observer.onComplete()
            }
        })
    }
    /* Observable region end*/

    /* Single region start*/
    fun <T> init(single: Single<T>, observer: SingleObserver<T>) {
        mModel.init(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun <T> restart(single: Single<T>, observer: SingleObserver<T>) {
        mModel.restart(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun <T> observeLiveData(owner: LifecycleOwner?, observer: SingleObserver<T>) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer<RxResult<T>> {
            when {
                it!!.error != null -> observer.onError(it.error!!)
                it.result != null -> observer.onSuccess(it.result)
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
    fun <T> init(flowable: Flowable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.init(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun <T> restart(flowable: Flowable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        mModel.restart(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }
    /* Flowable region end*/

    /* Maybe region start*/
    fun <T> init(maybe: Maybe<T>, observer: MaybeObserver<T>) {
        mModel.init(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun <T> restart(maybe: Maybe<T>, observer: MaybeObserver<T>) {
        mModel.restart(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun <T> observeLiveData(owner: LifecycleOwner?, observer: MaybeObserver<T>) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer<RxResult<T>> {
            when {
                it!!.error != null -> observer.onError(it.error!!)
                it.result != null -> observer.onSuccess(it.result)
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

    @Suppress("UNCHECKED_CAST")
    private fun observeLiveData(owner: LifecycleOwner?, observer: CompletableObserver) {
        if (owner == null) {
            return
        }

        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer<RxResult<Any>> {
            when {
                it!!.error != null -> observer.onError(it.error!!)
                else -> observer.onComplete()
            }
        })
    }
    /* Completable region end*/
}

