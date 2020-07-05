package com.blackfrogweb.mercadofinder.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.domain.interfaces.OnSearchItemListener
import com.blackfrogweb.mercadofinder.presentation.constants.SEARCH_ITEM_KEY
import com.blackfrogweb.mercadofinder.presentation.fragments.DetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSearchItemListener {

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTwoPane = activity_main_fragment_detail != null
    }

    override fun onSearchItemClicked(searchItem: SearchItem) {
        if(mTwoPane) {
            (activity_main_fragment_detail as? DetailFragment)?.showItemDetail(searchItem)
        } else {
            val detailIntent = Intent(this, DetailActivity::class.java)
            detailIntent.putExtra(SEARCH_ITEM_KEY, searchItem)
            startActivity(detailIntent)
        }
    }
}