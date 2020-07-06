package com.blackfrogweb.mercadofinder.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.domain.interfaces.OnSearchItemListener
import com.blackfrogweb.mercadofinder.data.constants.SEARCH_ITEM_KEY
import com.blackfrogweb.mercadofinder.presentation.fragments.SearchFragment
import com.blackfrogweb.mercadofinder.presentation.fragments.DetailFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity where the user searches for the desired articles.
 * This activity may host a [SearchFragment] and a [DetailActivity]
 * in two-pane mode (on tablets) or just a [SearchFragment]
 * on handsets.
 */
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