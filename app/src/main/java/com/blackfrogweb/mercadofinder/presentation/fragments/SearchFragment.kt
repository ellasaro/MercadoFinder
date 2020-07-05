package com.blackfrogweb.mercadofinder.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.MessageViewState
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.domain.entities.SearchViewState
import com.blackfrogweb.mercadofinder.domain.interfaces.OnSearchItemListener
import com.blackfrogweb.mercadofinder.presentation.activities.DetailActivity
import com.blackfrogweb.mercadofinder.presentation.activities.MainActivity
import com.blackfrogweb.mercadofinder.presentation.adapters.AdapterSearchItem
import com.blackfrogweb.mercadofinder.presentation.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.layout_user_message.view.*

/**
 * Fragment used to first_search for an article.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [DetailActivity]
 * on handsets.
 */
class SearchFragment : Fragment(), OnSearchItemListener {

    private var mSearchViewModel: SearchViewModel? = null
    private var mInteractionListener: OnSearchItemListener? = null
    private var mSearchItemAdapter: AdapterSearchItem? = null
    private var v: View? = null
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is OnSearchItemListener) {
            mInteractionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mInteractionListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_search, container, false)

        mSearchItemAdapter = AdapterSearchItem(this)
        v?.fragment_search_recycler_view?.adapter = mSearchItemAdapter

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v?.fragment_search_edit_text_search_field?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v?.fragment_search_edit_text_search_field?.text?.toString()
                        ?.isNotEmpty() == true
                ) {
                    v?.fragment_search_edit_text_search_field?.clearFocus()
                    hideKeyboard(v?.fragment_search_edit_text_search_field)
                    mSearchViewModel?.searchTerm(
                        v?.fragment_search_edit_text_search_field?.text?.toString() ?: ""
                    )
                } else {
                    showToast(getString(R.string.empty_search_field_message))
                }
                true
            } else {
                false
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mSearchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)

        mSearchViewModel?.messageState?.observe(viewLifecycleOwner, Observer {
            handleMessageState(it)
        })

        mSearchViewModel?.viewState?.observe(viewLifecycleOwner, Observer {
            handleViewState(it)
        })
    }

    private fun handleMessageState(messageViewState: MessageViewState?) {
        messageViewState?.let {
            v?.layout_user_message_image_view?.setImageDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    it.drawableResourceId
                )
            )
            v?.layout_user_message_text_view?.text = getString(it.messageResourceId)
            v?.fragment_search_layout_user_message?.visibility = View.VISIBLE
        } ?: run {
            v?.fragment_search_layout_user_message?.visibility = View.GONE
        }
    }

    private fun handleViewState(viewState: SearchViewState) {
        v?.fragment_search_progress_bar?.visibility =
            if (viewState.isLoading) View.VISIBLE else View.GONE
        mSearchItemAdapter?.setData(viewState.searchResult)
    }

    override fun onSearchItemClicked(searchItem: SearchItem) {
        mInteractionListener?.onSearchItemClicked(searchItem)
    }

    private fun hideKeyboard(v: View?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(v?.windowToken, 0)
    }

    private fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }
}
