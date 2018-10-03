package com.github.VyacheslavShmakin.rx.vmt;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * RxBaseTask
 *
 * @author Vyacheslav Shmakin
 * @version 02.10.2018
 */
public class RxBaseTask<T> {

    private final WeakReference<ViewModelStoreOwner> mRef;
    protected final RxViewModel<T> mModel;

    public RxBaseTask(@NonNull ViewModelStoreOwner owner, @NonNull String taskId) {
        mRef = new WeakReference<>(owner);
        mModel = createViewModel(taskId);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private RxViewModel<T> createViewModel(@NonNull String taskId) {
        if (mRef == null || mRef.get() == null) {
            throw new NullPointerException("ViewModelStoreOwner should be defined!");
        } else if (TextUtils.isEmpty(taskId)) {
            throw new IllegalStateException("TaskId should be defined as non-null and non-empty value!");
        } else if (mRef.get() instanceof Fragment) {
            return (RxViewModel<T>) ViewModelProviders.of((Fragment) mRef.get()).get(taskId, RxViewModel.class);
        } else if (mRef.get() instanceof FragmentActivity) {
            return (RxViewModel<T>) ViewModelProviders.of((FragmentActivity) mRef.get()).get(taskId, RxViewModel.class);
        }
        throw new IllegalStateException("Owner should be defined as Fragment or FragmentActivity");
    }

    @Nullable
    protected LifecycleOwner getLifeCycleOwner() {
        if (mRef == null || mRef.get() == null) {
            return null;
        }

        ViewModelStoreOwner storeOwner = mRef.get();
        if (storeOwner instanceof Fragment)  {
            return ((Fragment) storeOwner);
        } else if (storeOwner instanceof FragmentActivity) {
            return ((FragmentActivity) storeOwner);
        }
        return null;
    }
}
