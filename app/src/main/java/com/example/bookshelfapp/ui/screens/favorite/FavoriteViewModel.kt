package com.example.bookshelfapp.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelfapp.data.database.mapToBookList
import com.example.bookshelfapp.data.database.mapToFavoriteBook
import com.example.bookshelfapp.data.repository.OfflineBookshelfRepository
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.FavoriteType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(
    private val offlineBookshelfRepository: OfflineBookshelfRepository
): ViewModel() {

    val favoriteUiState: StateFlow<FavoriteUiState> =
        offlineBookshelfRepository.getFavoriteBooksStream()
            .map { FavoriteUiState(it.mapToBookList()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = FavoriteUiState()
            )

    suspend fun updateFavoriteBook(book: Book) {
        if (book.favoriteType == FavoriteType.FAVORITE) {
            offlineBookshelfRepository.removeFavoriteBook(book.mapToFavoriteBook())
        } else {
            offlineBookshelfRepository.addFavoriteBook(book.mapToFavoriteBook())
        }
    }
}

data class FavoriteUiState(
    val favoriteBooks: List<Book> = emptyList()
)
