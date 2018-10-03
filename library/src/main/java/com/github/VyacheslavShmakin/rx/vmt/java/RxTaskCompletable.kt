package com.github.VyacheslavShmakin.rx.vmt.java

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelStoreOwner
import com.github.VyacheslavShmakin.rx.vmt.RxBaseTask
import io.reactivex.Completable
import io.reactivex.CompletableObserver

/**
 * RxTaskCompletable
 *
 * @author Vyacheslav Shmakin
 * @version 03.10.2018
 */
class RxTaskCompletable private constructor(
        owner: ViewModelStoreOwner,
        id: String) : RxBaseTask<Any>(owner, id) {

    companion object {
        @JvmStatic
        fun create(
                owner: ViewModelStoreOwner,
                id: String): RxTaskCompletable {

            return RxTaskCompletable(owner, id)
        }
    }

    fun init(completable: Completable, observer: CompletableObserver) {
        mModel.init(completable)
        observeLiveData(lifeCycleOwner, observer)
    }

    fun restart(completable: Completable, observer: CompletableObserver) {
        mModel.restart(completable)
        observeLiveData(lifeCycleOwner, observer)
    }

    private fun observeLiveData(owner: LifecycleOwner?, observer: CompletableObserver) {
        if (owner == null) {
            return
        }
        mModel.liveData!!.observe(owner, android.arch.lifecycle.Observer { data ->
            when {
                data!!.error != null -> observer.onError(data.error!!)
                else -> observer.onComplete()
            }
        })
    }
}

