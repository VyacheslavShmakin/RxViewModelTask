package ru.shmakinv.rxviewmodeltask

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single


/**
 * RxViewModel
 *
 * @author Vyacheslav Shmakin
 * @version 30.09.2018
 */
internal class RxViewModel<T> : ViewModel() {

    var mLiveData: RxLiveData<T>? = null
    private var observable: Observable<T>? = null
    private var single: Single<T>? = null

    /* Observable region start*/
    fun init(o: Observable<T>, collectAll: Boolean) {
        if (observable == null) {
            restart(o, collectAll)
        }
    }

    fun restart(o: Observable<T>, collectAll: Boolean) {
        mLiveData?.stopCalculation()
        observable = o
        single = null
        mLiveData = RxLiveData(observable!!, collectAll)
    }
    /* Observable region end*/
    /* Single region start*/
    fun init(o: Single<T>) {
        if (observable == null) {
            restart(o)
        }
    }

    fun restart(o: Single<T>) {
        mLiveData?.stopCalculation()
        single = o
        observable = null
        mLiveData = RxLiveData(single!!)
    }

    /* Single region end*/
    override fun onCleared() {
        super.onCleared()
        mLiveData?.stopCalculation()
        observable = null
        single = null
    }
}
