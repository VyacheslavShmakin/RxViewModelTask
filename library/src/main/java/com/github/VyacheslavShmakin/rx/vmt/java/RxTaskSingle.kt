package com.github.VyacheslavShmakin.rx.vmt.java

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import com.github.VyacheslavShmakin.rx.vmt.RxBaseTask
import io.reactivex.Single
import io.reactivex.SingleObserver

/**
 * RxTaskSingle
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
class RxTaskSingle<T> private constructor(
        owner: ViewModelStoreOwner,
        id: String,
        private val single: Single<T>) : RxBaseTask<T>(owner, id) {

    companion object {
        @JvmStatic
        fun <T> create(
                owner: ViewModelStoreOwner,
                id: String,
                single: Single<T>): RxTaskSingle<T> {

            return RxTaskSingle(owner, id, single)
        }
    }

    fun init(observer: SingleObserver<T>) {
        mModel.init(single)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(owner: LifecycleOwner, observer: SingleObserver<T>) {
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
}
