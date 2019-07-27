package com.github.VyacheslavShmakin.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 *
 * RxUtils
 * @author Vyacheslav Shmakin
 * @version 27.07.2018
 */
class RxUtils {
    companion object {
        private val TAG = RxUtils::class.java.simpleName

        @JvmStatic
        fun <T> async(): SingleTransformer<T, T> {
            return SingleTransformer {
                it.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun <T> asyncObservable(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun <T> asyncFlowable(): FlowableTransformer<T, T> {
            return FlowableTransformer {
                it.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun <T> asyncMaybe(): MaybeTransformer<T, T> {
            return MaybeTransformer {
                it.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun asyncCompletable(): CompletableTransformer {
            return CompletableTransformer {
                it.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmOverloads
        @JvmStatic
        fun <T> wrap(
                onNext: (T) -> Unit,
                onError: ((Throwable?) -> Unit)? = null,
                onComplete: (() -> Unit)? = null): Observer<T> {

            return object : Observer<T> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: T) {
                    onNext.invoke(t)
                }

                override fun onError(e: Throwable) {
                    onError?.invoke(e)
                }

                override fun onComplete() {
                    onComplete?.invoke()
                }
            }
        }

        @JvmOverloads
        @JvmStatic
        fun <T> wrapSingle(
                onSuccess: (T) -> Unit,
                onError: ((Throwable) -> Unit)? = null): SingleObserver<T> {

            return object : SingleObserver<T> {
                override fun onSuccess(t: T) {
                    onSuccess.invoke(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    onError?.invoke(e)
                }
            }
        }

        @JvmOverloads
        @JvmStatic
        fun <T> wrapMaybe(
                onSuccess: (T) -> Unit,
                onError: ((Throwable) -> Unit)? = null,
                onComplete: (() -> Unit)? = null): MaybeObserver<T> {

            return object : MaybeObserver<T> {
                override fun onComplete() {
                    onComplete?.invoke()
                }

                override fun onSuccess(t: T) {
                    onSuccess.invoke(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    onError?.invoke(e)
                }
            }
        }

        @JvmOverloads
        @JvmStatic
        fun wrapCompletable(
                onComplete: () -> Unit,
                onError: ((Throwable) -> Unit)? = null): CompletableObserver {

            return object : CompletableObserver {
                override fun onComplete() {
                    onComplete.invoke()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    onError?.invoke(e)
                }
            }
        }
    }
}