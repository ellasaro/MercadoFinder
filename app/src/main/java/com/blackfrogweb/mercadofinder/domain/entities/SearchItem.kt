package com.blackfrogweb.mercadofinder.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchItem(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String?,
    @SerializedName("price") var price: Double?,
    @SerializedName("installments") var installments: Installments?,
    @SerializedName("currency_id") var currencyId: String?,
    @SerializedName("sold_quantity") var soldQuantity: Int?,
    @SerializedName("available_quantity") var availableQuantity: Int?,
    @SerializedName("condition") var condition: String?,
    @SerializedName("thumbnail") var thumbnail: String?,
    @SerializedName("permalink") var link: String?,
    @SerializedName("shipping") var shipping: ShippingInformation?,
    @SerializedName("attributes") var attributes: ArrayList<ItemAttribute> = arrayListOf()
) : Serializable