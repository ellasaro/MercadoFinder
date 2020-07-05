package com.blackfrogweb.mercadofinder.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.domain.interfaces.OnSearchItemListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_search_item.view.*

class AdapterSearchItem(private val listener: OnSearchItemListener) :
    RecyclerView.Adapter<AdapterSearchItem.SearchItemViewHolder>() {

    private var searchItemList: List<SearchItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_search_item, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.setItem(searchItemList[position])
        holder.bind(searchItemList[position], listener)
    }

    class SearchItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun setItem(searchItem: SearchItem) {
            view.adapter_search_item_text_view_price.text =
                view.context.getString(R.string.price_format, searchItem.price.toString())
            view.adapter_search_item_text_view_title.text = searchItem.title
            view.adapter_search_item_text_view_condition.text = searchItem.condition

            Glide.with(view.context).load(searchItem.thumbnail)
                .into(view.adapter_search_item_image_view_thumbnail)
        }

        fun bind(searchItem: SearchItem, listener: OnSearchItemListener) {
            itemView.setOnClickListener {
                listener.onSearchItemClicked(searchItem)
            }
        }
    }

    fun setData(data: ArrayList<SearchItem>) {
        searchItemList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return searchItemList.size
    }
}