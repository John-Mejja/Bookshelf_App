package com.example.bookshelfapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.ui.screens.detail.DetailViewModel
import com.example.bookshelfapp.ui.screens.favorite.FavoriteViewModel
import com.example.bookshelfapp.ui.screens.home.HomeViewModel
import com.example.bookshelfapp.ui.screens.query.QueryViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                networkBookshelfRepository = bookshelfApplication().container.networkBookshelfRepository,
            )
        }
        initializer {
            QueryViewModel(
                bookshelfNetworkRepository = bookshelfApplication().container.networkBookshelfRepository
            )
        }
        initializer {
            DetailViewModel(
                bookshelfNetworkRepository = bookshelfApplication().container.networkBookshelfRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }
        initializer {
            FavoriteViewModel(
                offlineBookshelfRepository = bookshelfApplication().container.offlineBookshelfRepository
            )
        }
    }
}

fun CreationExtras.bookshelfApplication() : BookshelfApplication =
    this[APPLICATION_KEY] as BookshelfApplication