package com.example.bookshelfapp.ui.screens.query

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelfapp.data.repository.NetworkBookshelfRepository
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.ui.screens.home.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QueryViewModel(
    private val bookshelfNetworkRepository: NetworkBookshelfRepository
) : ViewModel() {

    private val _queryUiState = MutableStateFlow(QueryUiState())
    val queryUiState = _queryUiState.asStateFlow()

    init {
        fetchRecommendedBooks("Computer")
    }
    fun fetchRecommendedBooks( query: String) {
        viewModelScope.launch {
            updateIsBookQuerySearchStarted(true)
            updateQueryResultsUiState(QueryResultsUiState.Loading)
            val booksResult = bookshelfNetworkRepository.getBooks(query)
            if (booksResult == null) {
                updateQueryResultsUiState(
                    QueryResultsUiState.Error(
                        errorMessage = "Check your Network Connection"
                    )
                )
            } else if (booksResult.isEmpty()) {
                updateQueryResultsUiState(
                    QueryResultsUiState.Error(
                        errorMessage = "Network resource not found."
                    )
                )
            } else {
                updateQueryResultsUiState(
                    QueryResultsUiState.Success(
                        queryBooksResults = booksResult
                    )
                )
            }
        }
    }
    //SearchState functions
    fun updateSearchQuery(query: String) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchState = currentUiState.searchState.copy(
                    query = query
                )
            )
        }
    }
    fun updateSearchActive(active: Boolean) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchState = currentUiState.searchState.copy(
                    active = active
                )
            )
        }
    }
    fun resetSearchState(active: Boolean = false) {
        _queryUiState.update { currentUiState ->
            currentUiState.copy(
                searchState = currentUiState.searchState.copy(
                    active = active,
                    query = ""
                )
            )
        }
    }
    fun clearSearch() {
        resetSearchState(active = true)
    }

    //RecommendationUiState Functions
    fun updateIsBookQuerySearchStarted( isBookQuerySearchStarted: Boolean) {
        _queryUiState.update { currentState ->
            currentState.copy(
                isBookQuerySearchStarted = isBookQuerySearchStarted
            )
        }
    }

    fun updateQueryResultsUiState( queryResultsUiState: QueryResultsUiState) {
        _queryUiState.update { currentState ->
            currentState.copy(
                queryResultsUiState = queryResultsUiState
            )
        }
    }
}
data class QueryUiState(
    val queryResultsUiState: QueryResultsUiState = QueryResultsUiState.Loading,
    val isBookQuerySearchStarted: Boolean = false,
    val searchState: SearchState = SearchState()
)
sealed interface QueryResultsUiState {
    data class Success(
        val queryBooksResults: List<Book> = emptyList()
    ): QueryResultsUiState
    data class Error(
        val errorMessage: String
    ): QueryResultsUiState
    data object Loading: QueryResultsUiState
}