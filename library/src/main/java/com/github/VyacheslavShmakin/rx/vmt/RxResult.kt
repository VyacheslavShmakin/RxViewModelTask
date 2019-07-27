package com.github.VyacheslavShmakin.rx.vmt

/**
 * RxResult
 *
 * @author Vyacheslav Shmakin
 * @version 26.07.2019
 */
open class RxResult<T> private constructor(
        val result: T?,
        val error: Throwable?) {

    companion object {
        @JvmStatic
        fun <T> onComplete(): RxResult<T> {
            return RxResult(null, null)
        }

        @JvmStatic
        fun <T> onNext(result: T?): RxResult<T> {
            return RxResult(result, null)
        }

        @JvmStatic
        fun <T> onError(error: Throwable?): RxResult<T> {
            return RxResult(null, error)
        }
    }
}
