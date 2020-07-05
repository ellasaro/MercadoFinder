package com.blackfrogweb.mercadofinder

import android.app.Application
import com.blackfrogweb.mercadofinder.data.RepositoryImpl
import com.blackfrogweb.mercadofinder.domain.Repository


class MercadoFinderApplication : Application() {

    companion object {
        private var instance : MercadoFinderApplication? = null

        fun getRepository() : Repository {
            return instance!!.repository
        }
    }

    init {
        instance = this
    }

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()

        repository = initRepo()
    }

    private fun initRepo(): Repository {
        return RepositoryImpl()
    }
}