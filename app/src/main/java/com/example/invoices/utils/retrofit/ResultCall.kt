package com.example.invoices.utils.retrofit

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, NetworkResult<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone())
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<NetworkResult<T>>
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val networkResult: NetworkResult<T>
            if (response.isSuccessful) {
                networkResult = NetworkResult.Success.HttpResponse(
                    value = response.body() as T,
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString(),
                )
            } else {
                networkResult = NetworkResult.Failure.HttpError(
                    HttpException(
                        statusCode = response.code(),
                        statusMessage = response.message(),
                        url = call.request().url.toString(),
                    )
                )
            }
            callback.onResponse(proxy, Response.success(networkResult))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val networkResult = when (error) {
                is retrofit2.HttpException -> NetworkResult.Failure.HttpError(
                    HttpException(error.code(), error.message(), cause = error)
                )

                is IOException -> NetworkResult.Failure.Error(error)
                else -> NetworkResult.Failure.Error(error)
            }

            callback.onResponse(proxy, Response.success(networkResult))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}