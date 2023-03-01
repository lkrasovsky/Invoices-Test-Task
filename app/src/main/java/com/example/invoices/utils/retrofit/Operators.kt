package com.example.invoices.utils.retrofit


fun <T> NetworkResult<T>.asSuccess(): NetworkResult.Success<T> {
    return this as NetworkResult.Success<T>
}

fun <T, R> NetworkResult<T>.map(transform: (value: T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success.Value(transform(value))
        is NetworkResult.Failure<*> -> this
    }
}
