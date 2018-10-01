package ru.shmakinv.rxviewmodeltask

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * RxLiveData
 *
 * @author Vyacheslav Shmakin
 * @version 30.09.2018
 */
internal class RxLiveData<T> : MutableLiveData<RxResult<T>> {

    private val collectedItems = ArrayDeque<RxResult<T>>()
    private var disposable: Disposable? = null
    private var isActive: Boolean = false
    private var collectAll: Boolean = false

    constructor(observable: Observable<T>, collectAll: Boolean) : super() {
        this.collectAll = collectAll
        disposable = observable.subscribe(
                { result -> collectOrPublish(RxResult.onNext(result), collectAll) },
                { t -> collectOrPublish(RxResult.onError(t), collectAll) },
                { collectOrPublish(RxResult.onComplete(), collectAll) })
    }

    private fun collectOrPublish(result: RxResult<T>, collectAll: Boolean) {
        if (!isActive && collectAll) {
            collectedItems.add(result)
        }
        value = result
    }

    constructor(single: Single<T>) : super() {
        disposable = single.subscribe(
                { result -> setValue(RxResult.onNext(result)) },
                { t -> setValue(RxResult.onError(t)) })
    }

    fun stopCalculation() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }

    override fun onInactive() {
        super.onInactive()
        isActive = false
    }

    override fun onActive() {
        super.onActive()
        isActive = true
        publishCollectedIfExist()
    }

    private fun publishCollectedIfExist() {
        if (collectAll && collectedItems.isNotEmpty()) {
            var item = collectedItems.poll()
            while (item != null) {
                value = item
                item = collectedItems.poll()
            }
        }
    }
}
