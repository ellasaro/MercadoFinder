package com.blackfrogweb.mercadofinder.domain.interfaces

import com.blackfrogweb.mercadofinder.domain.entities.SearchItem

interface OnSearchItemListener {
    fun onSearchItemClicked(searchItem: SearchItem)
}