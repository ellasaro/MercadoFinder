package com.blackfrogweb.mercadofinder.data.logging.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.lang.Exception

object CrashLogger {

    fun logEvent(location: String, eventDetails: String) {
        FirebaseCrashlytics.getInstance().log("$location -> $eventDetails")
    }

    fun logException(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }

}