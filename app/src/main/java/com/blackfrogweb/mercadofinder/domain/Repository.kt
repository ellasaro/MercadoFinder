package com.blackfrogweb.mercadofinder.domain

import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import io.reactivex.Single

interface Repository {
    fun searchTerm(term: String): Single<ArrayList<SearchItem>>
}
