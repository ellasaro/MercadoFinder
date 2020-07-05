package com.blackfrogweb.mercadofinder.domain.entities

data class SearchViewState(
    val isLoading: Boolean = false,
    val searchResult: ArrayList<SearchItem> = arrayListOf()
)