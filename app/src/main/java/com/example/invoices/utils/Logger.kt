package com.example.invoices.utils

import android.util.Log

object Logger {

    private const val defaultTag = "AAA"

    fun log(message: String, tag: String = defaultTag) {
        Log.d(tag, message)
    }

    fun error(e: Throwable, tag: String = defaultTag) {
        Log.e(tag, "Error!", e)
    }

    fun error(msg: String, tag: String = defaultTag) {
        Log.e(tag, "Error! $msg")
    }

}