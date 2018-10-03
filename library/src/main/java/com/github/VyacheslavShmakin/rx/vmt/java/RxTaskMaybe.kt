package com.github.VyacheslavShmakin.rx.vmt.java

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import com.github.VyacheslavShmakin.rx.vmt.RxBaseTask
import io.reactivex.Maybe
import io.reactivex.MaybeObserver

/**
 * RxTaskMaybe
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
class RxTaskMaybe<T> private constructor(
        owner: ViewModelStoreOwner,
        id: String,
        private val maybe: Maybe<T>) : RxBaseTask<T>(owner, id) {

    companion object {
        @JvmStatic
        fun <T> create(
                owner: ViewModelStoreOwner,
                id: String,
                maybe: Maybe<T>): RxTaskMaybe<T> {

            return RxTaskMaybe(owner, id, maybe)
        }
    }

    fun init(observer: MaybeObserver<T>) {
        mModel.init(maybe)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(observer: MaybeObserver<T>) {
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
}

