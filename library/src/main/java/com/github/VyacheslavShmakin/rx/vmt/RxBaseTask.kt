package com.github.VyacheslavShmakin.rx.vmt

import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.ref.WeakReference

/**
 * RxBaseTask
 *
 * @author Vyacheslav Shmakin
 * @version 27.07.2019
 */
open class RxBaseTask(owner: ViewModelStoreOwner, taskId: String) {

    private val ref: WeakReference<ViewModelStoreOwner>?
    protected val model: RxViewModel

    init {
        ref = WeakReference(owner)
        model = createViewModel(taskId)
    }

    protected val lifeCycleOwner: LifecycleOwner?
        get() {
            ref?.get()?.let {
                return when(it) {
                    is Fragment -> it
                    is FragmentActivity -> it
                    else -> null
                }
            }
            return null
        }

    private fun createViewModel(taskId: String): RxViewModel {
        return when {
            ref?.get() == null -> throw NullPointerException("ViewModelStoreOwner should be defined!")
            TextUtils.isEmpty(taskId) -> throw IllegalStateException("TaskId should be defined as non-null and non-empty value!")
            ref.get() is Fragment -> ViewModelProviders.of(ref.get() as Fragment).get(RxViewModel::class.java)
            ref.get() is FragmentActivity -> ViewModelProviders.of(ref.get() as FragmentActivity).get(RxViewModel::class.java)
            else -> throw IllegalStateException("Owner should be defined as Fragment or FragmentActivity")
        }
    }
}
