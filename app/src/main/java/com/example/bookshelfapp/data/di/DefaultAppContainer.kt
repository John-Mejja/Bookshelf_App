package com.example.bookshelfapp.data.di

import android.content.Context
import com.example.bookshelfapp.data.database.BookshelfDatabase
import com.example.bookshelfapp.data.network.GoogleApisBookService
import com.example.bookshelfapp.data.repository.NetworkBookshelfRepository
import com.example.bookshelfapp.data.repository.NetworkServiceBookshelfRepository
import com.example.bookshelfapp.data.repository.OfflineBookshelfRepository
import com.example.bookshelfapp.data.repository.OfflineServiceRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    override val googleApisBookService: GoogleApisBookService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GoogleApisBookService.BASE_URL)
            .build()
            .create()
    }
    override val networkBookshelfRepository: NetworkBookshelfRepository by lazy {
        NetworkServiceBookshelfRepository(googleApisBookService = googleApisBookService)
    }
    override val offlineBookshelfRepository: OfflineBookshelfRepository by lazy {
        OfflineServiceRepository(BookshelfDatabase.getDatabase(context).favoriteDao())
    }
}