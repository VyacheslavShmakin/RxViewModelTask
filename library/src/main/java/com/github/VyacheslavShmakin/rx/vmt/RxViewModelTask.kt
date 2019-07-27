package com.github.VyacheslavShmakin.rx.vmt

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import io.reactivex.*

/**
 * RxViewModelTask
 *
 * @author Vyacheslav Shmakin
 * @version 27.07.2019
 */
class RxViewModelTask private constructor(
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
    fun <T : Any> init(observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        model.init(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun <T : Any> restart(observable: Observable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        model.restart(observable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> observeLiveData(owner: LifecycleOwner?, observer: Observer<T>) {
        owner?.let { own ->
            model.liveData?.observe(own, androidx.lifecycle.Observer {
                when {
                    it!!.error != null -> observer.onError(it.error!!)
                    it.result != null -> observer.onNext(it.result as T)
                    else -> observer.onComplete()
                }
            })
        }
    }
    /* Observable region end*/

    /* Single region start*/
    fun <T : Any> init(single: Single<T>, observer: SingleObserver<T>) {
        model.init(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun <T : Any> restart(single: Single<T>, observer: SingleObserver<T>) {
        model.restart(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> observeLiveData(owner: LifecycleOwner?, observer: SingleObserver<T>) {
        owner?.let { own ->
            model.liveData?.observe(own, androidx.lifecycle.Observer {
                when {
                    it!!.error != null -> observer.onError(it.error!!)
                    it.result != null -> observer.onSuccess(it.result as T)
                }
            })
        }
    }
    /* Single region end*/

    /* Flowable region start*/
    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun <T : Any> init(flowable: Flowable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        model.init(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun <T : Any> restart(flowable: Flowable<T>, observer: Observer<T>, collectAll: Boolean = false) {
        model.restart(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }
    /* Flowable region end*/

    /* Maybe region start*/
    fun <T : Any> init(maybe: Maybe<T>, observer: MaybeObserver<T>) {
        model.init(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun <T : Any> restart(maybe: Maybe<T>, observer: MaybeObserver<T>) {
        model.restart(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> observeLiveData(owner: LifecycleOwner?, observer: MaybeObserver<T>) {
        owner?.let { own ->
            model.liveData?.observe(own, androidx.lifecycle.Observer {
                when {
                    it!!.error != null -> observer.onError(it.error!!)
                    it.result != null -> observer.onSuccess(it.result as T)
                    else -> observer.onComplete()
                }
            })
        }
    }
    /* Maybe region end*/

    /* Completable region start*/
    fun init(completable: Completable, observer: CompletableObserver) {
        model.init(completable)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(completable: Completable, observer: CompletableObserver) {
        model.restart(completable)
        observeLiveData(lifeCycleOwner, observer)
    }

    @Suppress("UNCHECKED_CAST")
    private fun observeLiveData(owner: LifecycleOwner?, observer: CompletableObserver) {
        owner?.let { own ->
            model.liveData?.observe(own, androidx.lifecycle.Observer {
                when {
                    it!!.error != null -> observer.onError(it.error!!)
                    else -> observer.onComplete()
                }
            })
        }
    }
    /* Completable region end*/

    fun stop() {
        model.stop()
    }
}

