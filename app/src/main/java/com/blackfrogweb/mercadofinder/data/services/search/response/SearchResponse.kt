package com.blackfrogweb.mercadofinder.data.services.search.response
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("paging") var paging: Paging?,
    @SerializedName("results") var itemList: ArrayList<SearchItem>?
)

data class Paging(
    @SerializedName("total") var temp: Int?,
    @SerializedName("offset") var pressure: Int?,
    @SerializedName("limit") var humidity: Int?,
    @SerializedName("primary_results") var temp_min: Int?
)