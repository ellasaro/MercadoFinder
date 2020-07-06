package com.blackfrogweb.mercadofinder.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShippingInformation(
    @SerializedName("free_shipping") var freeShipping: Boolean = false
) : Serializable