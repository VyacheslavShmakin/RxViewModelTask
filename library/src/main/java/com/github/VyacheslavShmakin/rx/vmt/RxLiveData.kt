package com.github.VyacheslavShmakin.rx.vmt

import androidx.lifecycle.MutableLiveData
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * RxLiveData
 *
 * @author Vyacheslav Shmakin
 * @version 26.07.2019
 */
class RxLiveData<T> : MutableLiveData<RxResult<T>> {

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

    constructor(single: Single<T>) : super() {
        disposable = single.subscribe(
                { result -> postValue(RxResult.onNext(result)) },
                { t -> postValue(RxResult.onError(t)) })
    }

    constructor(completable: Completable) : super() {
        disposable = completable.subscribe(
                { value = RxResult.onComplete() },
                { t -> postValue(RxResult.onError(t)) })
    }

    constructor(flowable: Flowable<T>, collectAll: Boolean) : super() {
        this.collectAll = collectAll
        disposable = flowable.subscribe(
                { result -> collectOrPublish(RxResult.onNext(result), collectAll) },
                { t -> collectOrPublish(RxResult.onError(t), collectAll) },
                { collectOrPublish(RxResult.onComplete(), collectAll) })
    }

    constructor(maybe: Maybe<T>) : super() {
        disposable = maybe.subscribe(
                { result -> postValue(RxResult.onNext(result)) },
                { t -> postValue(RxResult.onError(t)) },
                { postValue(RxResult.onComplete()) })
    }

    private fun collectOrPublish(result: RxResult<T>, collectAll: Boolean) {
        if ((!isActive || collectedItems.isNotEmpty()) && collectAll) {
            collectedItems.add(result)
        }
        postValue(result)
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
                postValue(item)
                item = collectedItems.poll()
            }
        }
    }
}
