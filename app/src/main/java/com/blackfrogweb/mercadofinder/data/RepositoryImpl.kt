package com.blackfrogweb.mercadofinder.data

import com.blackfrogweb.mercadofinder.data.logging.crashlytics.CrashLogger
import com.blackfrogweb.mercadofinder.data.services.search.declaration.SearchService
import com.blackfrogweb.mercadofinder.data.services.search.response.SearchResponse
import com.blackfrogweb.mercadofinder.domain.Repository
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import io.reactivex.Single
import okhttp3.internal.http2.StreamResetException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlin.collections.ArrayList


class RepositoryImpl : Repository {

    private val location = this::class.java.simpleName
    private val searchService: SearchService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/sites/MLA/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        searchService = retrofit.create(SearchService::class.java)
    }

    /**
     * Gets from the MeracdoLibre api a list of items based on a user-entered search string
     * @param term is a String that the user has input in the search field
     * @return [Single] list of [SearchItem] that match the criterion
     */
    override fun searchTerm(term: String): Single<ArrayList<SearchItem>> {

        val call = searchService.getSearchResultsForTerm(term)

        return Single.create { emitter ->
            call.enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        val resultList = response.body()?.itemList ?: arrayListOf()
                        emitter.onSuccess(resultList)
                    } else {
                        CrashLogger.logEvent(location, "searchTerm unsuccessful with code ${response.code()}")
                        emitter.onError(Throwable("Unexpected error"))
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    CrashLogger.logEvent(location, "searchTerm fail ${t.message}")
                    val errorMessage = if (isConnectionException(t)) {
                        "Connection error"
                    } else {
                        "Unexpected error"
                    }
                    emitter.onError(Throwable(errorMessage))
                }
            })
        }
    }

    private fun isConnectionException(t: Throwable): Boolean {
        return (t is UnknownHostException ||
                t is SocketTimeoutException ||
                t is StreamResetException ||
                t is SSLHandshakeException ||
                t is ConnectException)
    }
}