package com.blackfrogweb.mercadofinder.presentation.helpers

import android.content.Context
import com.blackfrogweb.mercadofinder.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object StringMapper {

    fun getFormattedInstallmentsString(context: Context, value: Double, installments: Int): String {
        val formattedValue = getFormattedPriceString(context, value)
        return context.getString(R.string.installments_format, formattedValue, installments.toString())
    }

    fun getFormattedPriceString(context: Context, value: Double): String {
        val formattedValue = DecimalFormat("0.#", DecimalFormatSymbols(Locale.US)).format(value)
        return context.getString(R.string.price_format, formattedValue)
    }

    fun getAvailableQuantity(context: Context, value: Int): String {
        return when (value) {
            0 -> context.getString(R.string.empty_amount)
            1 -> context.getString(R.string.available_first_range)
            50 -> context.getString(R.string.available_second_range)
            else -> context.getString(R.string.available_third_range)
        }
    }

    fun getSoldQuantity(context: Context, value: Int): String {
        return when (value) {
            0 -> context.getString(R.string.empty_amount)
            5 -> value.toString()
            25 -> context.getString(R.string.sold_second_range)
            100 -> context.getString(R.string.sold_third_range)
            else -> context.getString(R.string.sold_fourth_range)
        }
    }
}