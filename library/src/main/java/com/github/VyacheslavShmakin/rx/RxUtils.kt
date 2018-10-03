package com.github.VyacheslavShmakin.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


/**
 *
 * RxUtils
 * @author Vyacheslav Shmakin
 * @version 24.04.2018
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
                onNext: Consumer<T>?,
                onError: Consumer<Throwable>? = null,
                onComplete: Action? = null): Observer<T> {

            return object : Observer<T> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: T) {
                    onNext?.accept(t)
                }

                override fun onError(e: Throwable) {
                    onError?.accept(e)
                }

                override fun onComplete() {
                    onComplete?.run()
                }
            }
        }

        @JvmOverloads
        @JvmStatic
        fun <T> wrapSingle(
                onSuccess: Consumer<T>,
                onError: Consumer<Throwable>? = null): SingleObserver<T> {

            return object : SingleObserver<T> {
                override fun onSuccess(t: T) {
                    onSuccess.accept(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    onError?.accept(e)
                }
            }
        }

        @JvmOverloads
        @JvmStatic
        fun <T> wrapMaybe(
                onSuccess: Consumer<T>?,
                onError: Consumer<Throwable>? = null,
                onComplete: Action? = null): MaybeObserver<T> {

            return object : MaybeObserver<T> {
                override fun onComplete() {
                    onComplete?.run()
                }

                override fun onSuccess(t: T) {
                    onSuccess?.accept(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    onError?.accept(e)
                }
            }
        }

        @JvmOverloads
        @JvmStatic
        fun wrapCompletable(
                onComplete: Action,
                onError: Consumer<Throwable>? = null): CompletableObserver {

            return object : CompletableObserver {
                override fun onComplete() {
                    onComplete.run()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    onError?.accept(e)
                }
            }
        }
    }
}