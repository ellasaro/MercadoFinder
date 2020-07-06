package com.blackfrogweb.mercadofinder.data.logging.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsLogger {

    fun logEventWithoutParams(context: Context, event: String) {
        FirebaseAnalytics.getInstance(context).logEvent(event, null)
    }

}