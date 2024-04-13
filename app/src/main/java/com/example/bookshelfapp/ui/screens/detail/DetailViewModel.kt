package com.example.bookshelfapp.ui.screens.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelfapp.data.repository.NetworkBookshelfRepository
import com.example.bookshelfapp.model.BOOK_ID_ARG
import com.example.bookshelfapp.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val bookshelfNetworkRepository: NetworkBookshelfRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    init {
        val bookId = checkNotNull(savedStateHandle.get<String>(BOOK_ID_ARG))
        updateBookId(bookId)
        fetchBook()
    }

    fun fetchBook( ) {
        viewModelScope.launch {
            val idQuery = detailUiState.value.bookId
            updateIsBookDetailQueryStarted(true)
            updateDetailResultsState(DetailResultsState.Loading)
            val bookResponse = bookshelfNetworkRepository.getBook(idQuery)
            Log.d("Response", "$bookResponse")
            if (bookResponse == null) {
                updateDetailResultsState(
                    DetailResultsState.Error("Network Error")
                )
            } else {
                updateDetailResultsState(
                    DetailResultsState.Success(bookResponse)
                )
            }
        }
    }
    fun updateIsBookDetailQueryStarted(isBookDetailQueryStarted: Boolean) {
        _detailUiState.update { currentState ->
            currentState.copy(
                isBookDetailQueryStarted = isBookDetailQueryStarted
            )
        }
    }
    fun updateBookId(bookId: String) {
        _detailUiState.update { currentState ->
            currentState.copy(
                bookId = bookId
            )
        }
    }
    private fun updateDetailResultsState(detailResultsState: DetailResultsState) {
        _detailUiState.update { currentState ->
            currentState.copy(
                detailResultsState = detailResultsState
            )
        }
    }

}
data class DetailUiState(
    val detailResultsState: DetailResultsState = DetailResultsState.Loading,
    val bookId: String = "Ke9hAAAAcAAj",
    val isBookDetailQueryStarted: Boolean = false
)
sealed interface DetailResultsState{
    data class Success(
        val bookDetail: Book
    ): DetailResultsState
    data class Error(
        val errorMessage: String
    ): DetailResultsState
    data object Loading: DetailResultsState
}