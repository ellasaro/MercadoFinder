package com.blackfrogweb.mercadofinder.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Installments(
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("amount") var amount: Double
) : Serializable