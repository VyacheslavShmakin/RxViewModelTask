package com.github.VyacheslavShmakin.rx.vmt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.*


/**
 * RxApplicationViewModel
 *
 * @author Vyacheslav Shmakin
 * @version 27.07.2019
 */
open class RxApplicationViewModel(app: Application) : AndroidViewModel(app) {

    var liveData: RxLiveData<*>? = null
        private set
    private var observable: Observable<*>? = null
    private var single: Single<*>? = null
    private var flowable: Flowable<*>? = null
    private var maybe: Maybe<*>? = null
    private var completable: Completable? = null

    /* Observable region start*/
    fun <T: Any> init(o: Observable<T>, collectAll: Boolean) {
        if (observable == null) {
            restart(o, collectAll)
        }
    }

    fun <T: Any> restart(o: Observable<T>, collectAll: Boolean) {
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
    fun <T: Any> init(o: Single<T>) {
        if (single == null) {
            restart(o)
        }
    }

    fun <T: Any> restart(o: Single<T>) {
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
    fun <T: Any> init(o: Flowable<T>, collectAll: Boolean) {
        if (flowable == null) {
            restart(o, collectAll)
        }
    }

    fun <T: Any> restart(o: Flowable<T>, collectAll: Boolean) {
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
    fun <T: Any> init(o: Maybe<T>) {
        if (maybe == null) {
            restart(o)
        }
    }

    fun <T: Any> restart(o: Maybe<T>) {
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
        liveData = RxLiveData<Any>(completable!!)
    }
    /* Completable region end*/

    fun stop() {
        liveData?.stopCalculation()
        observable = null
        single = null
        flowable = null
        completable = null
        maybe = null
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}
