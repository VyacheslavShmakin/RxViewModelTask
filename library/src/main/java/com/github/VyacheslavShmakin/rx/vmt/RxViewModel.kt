package com.github.VyacheslavShmakin.rx.vmt

import android.arch.lifecycle.ViewModel
import io.reactivex.*


/**
 * RxViewModel
 *
 * @author Vyacheslav Shmakin
 * @version 30.09.2018
 */
internal class RxViewModel<T> : ViewModel() {

    var liveData: RxLiveData<T>? = null
        private set
    private var observable: Observable<T>? = null
    private var single: Single<T>? = null
    private var flowable: Flowable<T>? = null
    private var maybe: Maybe<T>? = null
    private var completable: Completable? = null

    /* Observable region start*/
    fun init(o: Observable<T>, collectAll: Boolean) {
        if (observable == null) {
            restart(o, collectAll)
        }
    }

    fun restart(o: Observable<T>, collectAll: Boolean) {
        liveData?.stopCalculation()
        observable = o
        single = null
        flowable = null
        completable = null
        maybe = null
        liveData = RxLiveData(observable!!, collectAll)
    }

    /* Observable region end*/
    /* Single region start*/
    fun init(o: Single<T>) {
        if (single == null) {
            restart(o)
        }
    }

    fun restart(o: Single<T>) {
        liveData?.stopCalculation()
        single = o
        observable = null
        flowable = null
        completable = null
        maybe = null
        liveData = RxLiveData(single!!)
    }

    /* Single region end*/
    /* Flowable region start*/
    fun init(o: Flowable<T>, collectAll: Boolean) {
        if (flowable == null) {
            restart(o, collectAll)
        }
    }

    fun restart(o: Flowable<T>, collectAll: Boolean) {
        liveData?.stopCalculation()
        single = null
        observable = null
        flowable = o
        completable = null
        maybe = null
        liveData = RxLiveData(flowable!!, collectAll)
    }
    /* Flowable region end*/

    /* Maybe region start*/
    fun init(o: Maybe<T>) {
        if (maybe == null) {
            restart(o)
        }
    }

    fun restart(o: Maybe<T>) {
        liveData?.stopCalculation()
        single = null
        observable = null
        flowable = null
        completable = null
        maybe = o
        liveData = RxLiveData(maybe!!)
    }
    /* Maybe region end*/

    /* Completable region start*/
    fun init(o: Completable) {
        if (completable == null) {
            restart(o)
        }
    }

    fun restart(o: Completable) {
        liveData?.stopCalculation()
        single = null
        observable = null
        flowable = null
        completable = o
        maybe = null
        liveData = RxLiveData(completable!!)
    }
    /* Completable region end*/

    override fun onCleared() {
        super.onCleared()
        liveData?.stopCalculation()
        observable = null
        single = null
        flowable = null
        completable = null
        maybe = null
    }
}
