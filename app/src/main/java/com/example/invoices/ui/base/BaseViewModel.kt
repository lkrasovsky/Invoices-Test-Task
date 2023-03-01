package com.example.invoices.ui.base

import androidx.lifecycle.ViewModel
import com.example.invoices.utils.*
import com.example.invoices.utils.retrofit.NetworkResult
import com.example.invoicestesttask.R
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException

open class BaseViewModel : ViewModel() {

    private val _showErrorMessageResEvent = MutableLiveEvent<Int>()
    val showErrorMessageResEvent = _showErrorMessageResEvent.share()

    private val _showErrorMessageEvent = MutableLiveEvent<String>()
    val showErrorMessageEvent = _showErrorMessageEvent.share()

    @Suppress("UNCHECKED_CAST")
    fun <T> NetworkResult<T>.handleNetworkResult(
        successBlock: (T) -> Unit,
        finalBlock: () -> Unit
    ) {
        when (this) {
            is NetworkResult.Failure<*> -> {
                when (this) {
                    is NetworkResult.Failure.HttpError -> {
                        this.statusMessage?.let {
                            _showErrorMessageEvent.publishEvent(it)
                            Logger.error(this.error)
                            return
                        }
                        _showErrorMessageResEvent.publishEvent(R.string.backend_internal_error)
                    }

                    is NetworkResult.Failure.Error -> {
                        if (this.error is JsonParseException || this.error is JsonIOException || this.error is MalformedJsonException) {
                            _showErrorMessageResEvent.publishEvent(R.string.malformed)
                        } else {
                            _showErrorMessageResEvent.publishEvent(R.string.connection_error)
                        }

                        Logger.error("Internal error")
                    }
                }
            }

            is NetworkResult.Success<*> -> {
                try {
                    successBlock(this.value as T)
                } catch (e: Exception) {
                    _showErrorMessageResEvent.publishEvent(R.string.internal_error)
                }
            }
        }
        finalBlock()
    }
}