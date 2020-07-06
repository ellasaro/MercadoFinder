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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blackfrogweb.mercadofinder.presentation.activities.*
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.data.constants.CONDITION_NEW
import com.blackfrogweb.mercadofinder.data.constants.EVENT_NAVIGATE_TO_PRODUCT_PAGE
import com.blackfrogweb.mercadofinder.data.logging.analytics.AnalyticsLogger
import com.blackfrogweb.mercadofinder.data.logging.crashlytics.CrashLogger
import com.blackfrogweb.mercadofinder.presentation.helpers.StringMapper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.layout_user_message.view.*

/**
 * Fragment used to see an item's detailed information. Loads the item's permalink.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [DetailActivity]
 * on handsets.
 */
class DetailFragment : Fragment() {

    private val location = this::class.java.simpleName
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
        v?.layout_user_message_image_view?.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.shopping_bag
            )
        )
        v?.layout_user_message_text_view?.text = getString(R.string.detail_fragment_welcome_message)
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

            v?.fragment_detail_text_view_price_value?.text =
                StringMapper.getFormattedPriceString(mContext, item.price ?: 0.toDouble())

            item.installments?.let {
                v?.fragment_detail_text_view_installments_value?.text =
                    StringMapper.getFormattedInstallmentsString(
                        mContext,
                        item.installments?.amount ?: 0.toDouble(),
                        item.installments?.quantity ?: 0
                    )
                v?.fragment_detail_text_view_installments_value?.visibility = View.VISIBLE
            }

            v?.fragment_detail_text_view_condition_value?.text =
                if (item.condition == CONDITION_NEW) mContext.getString(R.string.condition_new) else mContext.getString(
                    R.string.condition_used
                )
            v?.fragment_detail_text_view_free_shipping_value?.text =
                if (item.shipping?.freeShipping == true) mContext.getString(R.string.yes) else mContext.getString(
                    R.string.no
                )
            v?.fragment_detail_text_view_available_value?.text =
                StringMapper.getAvailableQuantity(mContext, item.availableQuantity ?: 0)
            v?.fragment_detail_text_view_sold_value?.text =
                StringMapper.getSoldQuantity(mContext, item.soldQuantity ?: 0)

            v?.fragment_detail_layout_user_message?.visibility = View.GONE
            v?.fragment_detail_card_view?.visibility = View.VISIBLE
        } ?: run {
            v?.fragment_detail_layout_user_message?.visibility = View.VISIBLE
            v?.fragment_detail_card_view?.visibility = View.GONE
        }
    }

    private fun navigateToItemPage(uri: String?) {
        AnalyticsLogger.logEventWithoutParams(mContext, EVENT_NAVIGATE_TO_PRODUCT_PAGE)
        CrashLogger.logEvent(location, "$EVENT_NAVIGATE_TO_PRODUCT_PAGE -> $uri")
        try {
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
        } catch (e: Exception) {
            CrashLogger.logException(e)
        }
    }
}
