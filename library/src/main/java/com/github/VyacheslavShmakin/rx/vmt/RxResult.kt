package com.github.VyacheslavShmakin.rx.vmt

/**
 * RxResult
 *
 * @author Vyacheslav Shmakin
 * @version 30.09.2018
 */
internal class RxResult<T> private constructor(
        val result: T?,
        val error: Throwable?) {
    companion object {

        fun <T> onComplete(): RxResult<T> {
            return RxResult(null, null)
        }

        fun <T> onNext(result: T?): RxResult<T> {
            return RxResult(result, null)
        }

        fun <T> onError(error: Throwable?): RxResult<T> {
            return RxResult(null, error)
        }
    }
}
