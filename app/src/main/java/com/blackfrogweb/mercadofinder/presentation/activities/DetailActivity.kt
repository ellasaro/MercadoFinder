package com.blackfrogweb.mercadofinder.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.blackfrogweb.mercadofinder.R
import com.blackfrogweb.mercadofinder.domain.entities.SearchItem
import com.blackfrogweb.mercadofinder.presentation.constants.SEARCH_ITEM_KEY
import com.blackfrogweb.mercadofinder.presentation.fragments.DetailFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_left)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val searchItem = intent?.getSerializableExtra(SEARCH_ITEM_KEY) as? SearchItem
        (activity_detail_fragment_search as? DetailFragment)?.showItemDetail(searchItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}