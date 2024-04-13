package com.example.bookshelfapp.data.di

import com.example.bookshelfapp.data.network.GoogleApisBookService
import com.example.bookshelfapp.data.repository.NetworkBookshelfRepository
import com.example.bookshelfapp.data.repository.OfflineBookshelfRepository

interface AppContainer {
    val googleApisBookService: GoogleApisBookService
    val networkBookshelfRepository: NetworkBookshelfRepository
    val offlineBookshelfRepository: OfflineBookshelfRepository
}