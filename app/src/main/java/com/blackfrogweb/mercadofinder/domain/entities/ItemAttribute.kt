package com.blackfrogweb.mercadofinder.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemAttribute(
    @SerializedName("name") var attributeName: String?,
    @SerializedName("value") var attributeValue: String?
) : Serializable