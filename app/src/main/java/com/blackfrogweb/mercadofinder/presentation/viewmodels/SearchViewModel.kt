package com.blackfrogweb.mercadofinder.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.blackfrogweb.mercadofinder.MercadoFinderApplication
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.MessageViewState
import com.blackfrogweb.mercadofinder.domain.entities.SearchViewState

class SearchViewModel : BaseViewModel() {

    var viewState: MutableLiveData<SearchViewState> = MutableLiveData()
    var messageState: MutableLiveData<MessageViewState?> = MutableLiveData()

    init {
        viewState.value = SearchViewState()
        messageState.value = MessageViewState(R.drawable.first_search, R.string.get_started_message)
    }

    fun searchTerm(term: String) {
        viewState.value = viewState.value?.copy(isLoading = true, searchResult = arrayListOf())
        messageState.value = null
        addDisposable(MercadoFinderApplication.getRepository().searchTerm(term).subscribe(
            { results ->
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    searchResult = results)
                messageState.value = if(results.isNotEmpty()) null else MessageViewState(R.drawable.empty_box, R.string.empty_search_list)
            }, {
                viewState.value = viewState.value?.copy(isLoading = false)
                when(it.message) {
                    "Connection error" -> messageState.value = MessageViewState(R.drawable.connection_error, R.string.connection_error_message)
                    else -> messageState.value = MessageViewState(R.drawable.sad_face, R.string.unexpected_error_message)
                }
            }))
    }
}