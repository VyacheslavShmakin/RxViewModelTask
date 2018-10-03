package com.github.VyacheslavShmakin.rx.vmt.java

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import com.github.VyacheslavShmakin.rx.vmt.RxBaseTask
import io.reactivex.Flowable
import io.reactivex.Observer

/**
 * RxTaskFlowable
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
class RxTaskFlowable<T> private constructor(
        owner: ViewModelStoreOwner,
        id: String,
        private val flowable: Flowable<T>) : RxBaseTask<T>(owner, id) {

    companion object {
        @JvmStatic
        fun <T> create(
                owner: ViewModelStoreOwner,
                id: String,
                flowable: Flowable<T>): RxTaskFlowable<T> {

            return RxTaskFlowable(owner, id, flowable)
        }
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun init(observer: Observer<T>, collectAll: Boolean = false) {
        mModel.init(flowable, collectAll)
        observeLiveData(lifeCycleOwner, observer)
    }

    /**
     * collectAll parameter collects all changes when ViewModel inactive and pushes them when ViewModel becomes active.
     * Please be careful if you wanna collect a lot of data to avoid UI freezes. Recommended to disable this option
     */
    @JvmOverloads
    fun restart(observer: Observer<T>, collectAll: Boolean = false) {
        mModel.restart(flowable, collectAll)
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

