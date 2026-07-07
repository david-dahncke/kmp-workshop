package com.workshop.kmp.platform

/**
 * Logger-Interface für austauschbare, testbare Logging-Abhängigkeit.
 * Wird über Koin injiziert — Android und iOS liefern eigene Implementierungen.
 */
interface Logger {
    fun log(message: String)
    fun logError(message: String, throwable: Throwable? = null)
}
