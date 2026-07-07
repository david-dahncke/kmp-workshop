package com.workshop.kmp.platform

import android.util.Log

class AndroidLogger : Logger {
    override fun log(message: String) { Log.d("KMP-Workshop", message) }
    override fun logError(message: String, throwable: Throwable?) { Log.e("KMP-Workshop", message, throwable) }
}
