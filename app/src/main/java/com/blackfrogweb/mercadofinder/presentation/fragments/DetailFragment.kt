package com.blackfrogweb.mercadofinder.presentation.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.blackfrogweb.mercadofinder.presentation.activities.*
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.presentation.helpers.Mapper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.view.*

/**
 * Fragment used to see an item's detailed information. Loads the item's permalink.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [DetailActivity]
 * on handsets.
 */
class DetailFragment : Fragment() {

    private lateinit var mContext: Context
    private var detailedItem: SearchItem? = null
    private var v: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detail, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showItemDetail(detailedItem)
    }

    fun showItemDetail(item: SearchItem?) {
        item?.let {
            v?.fragment_detail_text_view_title?.text = item.title
            v?.fragment_detail_button_go_to_page?.setOnClickListener {
                navigateToItemPage(item.link)
            }

            Glide.with(mContext).load(item.thumbnail)
                .placeholder(R.drawable.image_placeholder)
                .into(v?.fragment_detail_image_view_thumbnail ?: AppCompatImageView(mContext))

            v?.fragment_detail_available_text_view_value?.text =
                Mapper.getAvailableQuantity(mContext, item.availableQuantity ?: 0)
            v?.fragment_detail_sold_text_view_value?.text =
                Mapper.getSoldQuantity(mContext, item.soldQuantity ?: 0)

            v?.fragment_detail_layout_user_message?.visibility = View.GONE
            v?.fragment_detail_card_view?.visibility = View.VISIBLE
        } ?: run {
            v?.fragment_detail_layout_user_message?.visibility = View.VISIBLE
            v?.fragment_detail_card_view?.visibility = View.GONE
        }
    }

    private fun navigateToItemPage(uri: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        if (browserIntent.resolveActivity(mContext.packageManager) != null) {
            startActivity(browserIntent)
        } else {
            Toast.makeText(
                mContext,
                getString(R.string.no_internet_browser_toast_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
