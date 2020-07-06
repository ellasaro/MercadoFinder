package com.blackfrogweb.mercadofinder.data.services.search.declaration

import com.blackfrogweb.mercadofinder.data.services.search.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService{

    @GET("search")
    fun getSearchResultsForTerm(@Query("q") searchTerm : String): Call<SearchResponse>

}