package com.blackfrogweb.mercadofinder.presentation.helpers

import android.content.Context

object Mapper {

    fun getAvailableQuantity(context: Context, value: Int): String {
        return when (value) {
            0 -> "-"
            1 -> "less than fifty"
            50 -> "less than a hundred"
            else -> "over a hundred"
        }
    }

    fun getSoldQuantity(context: Context, value: Int): String {
        return when (value) {
            0 -> "-"
            5 -> value.toString()
            25 -> "less than 50"
            100 -> "less than 100"
            else -> "over a hundred"
        }
    }
}