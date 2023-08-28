package de.skash.timetrack.core.helper.rx

import de.skash.timetrack.core.helper.state.ErrorType
import de.skash.timetrack.core.helper.state.ResourceState
import io.reactivex.rxjava3.core.Observable

fun <T : Any> Observable<T>.toState(errorType: (Throwable) -> ErrorType): Observable<ResourceState<T>> {
    return Observable.concat(
        Observable.just(ResourceState.Loading()),
        this.map<ResourceState<T>> {
            ResourceState.Success(it)
        }.onErrorReturn {
            //  val httpError = it as? HttpException ?: return@onErrorReturn State.Error(errorType(it))
            ResourceState.Error(errorType(it)) //ErrorType.fromThrowable(httpError) ?: errorType(it))
        }.doOnNext {
            println("test")
        }
    )
}