package com.workshop.kmp.platform

class IosLogger : Logger {
    override fun log(message: String) { println("[KMP-Workshop] $message") }
    override fun logError(message: String, throwable: Throwable?) { println("[KMP-Workshop][ERROR] $message ${throwable?.message ?: ""}") }
}
