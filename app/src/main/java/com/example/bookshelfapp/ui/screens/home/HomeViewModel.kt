package com.example.bookshelfapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelfapp.data.local.LocalData
import com.example.bookshelfapp.data.repository.NetworkBookshelfRepository
import com.example.bookshelfapp.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val networkBookshelfRepository: NetworkBookshelfRepository,
): ViewModel(){

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState:StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        fetchRecommendedBooks()
    }
    fun fetchRecommendedBooks() {
        viewModelScope.launch {
            val selectedTabIndex = homeUiState.value.recommendationUiState.pagerState.selectedTabIndex
            val query = homeUiState.value.recommendationUiState.pagerState.recommendedCollections[selectedTabIndex]
            updateIsRecommendedBooksSearching(true)
            updateRecommendationResultsState(RecommendationResultsState.Loading)
            val booksResult = networkBookshelfRepository.getBooks(query)

            if (booksResult == null) {
                updateRecommendationResultsState(
                    RecommendationResultsState.Error(
                        errorMessage = "Check your Network Connection"
                    )
                )
            } else if (booksResult.isEmpty()) {
                updateRecommendationResultsState(
                    RecommendationResultsState.Error(
                        errorMessage = "Network resource not found."
                    )
                )
            } else {
                updateRecommendationResultsState(
                    RecommendationResultsState.Success(
                        recommendedBooks = booksResult
                    )
                )
            }
        }
    }

    //RecommendationUiState Functions
    fun updateIsRecommendedBooksSearching( isRecommendedBooksSearching: Boolean) {
        _homeUiState.update {currentState ->
            currentState.copy(
                recommendationUiState = currentState.recommendationUiState.copy(
                    isRecommendedBooksSearching = isRecommendedBooksSearching
                )
            )
        }
    }
    fun updateSelectedTabIndex( selectedTabIndex: Int) {
        _homeUiState.update { currentState ->
            currentState.copy(
                recommendationUiState = currentState.recommendationUiState.copy(
                    pagerState = currentState.recommendationUiState.pagerState.copy(
                        selectedTabIndex = selectedTabIndex
                    )
                )
            )
        }
    }
    private fun updateRecommendationResultsState( recommendationResultsState: RecommendationResultsState) {
        _homeUiState.update { currentState ->
            currentState.copy(
                recommendationUiState = currentState.recommendationUiState.copy(
                    recommendationResultsState = recommendationResultsState
                )
            )
        }
    }
}




data class HomeUiState(
    val searchState: SearchState = SearchState(),
    val recommendationUiState: RecommendationUiState = RecommendationUiState()
)
data class PagerState(
    val selectedTabIndex: Int = 0,
    val recommendedCollections : List<String> = LocalData.recommendedCollections
)
data class SearchState(
    val query: String = "",
    val active: Boolean = false,
    val searchHistories: List<String> = emptyList()
)
data class RecommendationUiState(
    val pagerState: PagerState = PagerState(),
    val isRecommendedBooksSearching: Boolean = false,
    val recommendationResultsState: RecommendationResultsState = RecommendationResultsState.Loading
)
sealed interface RecommendationResultsState {
    data class Success(
        val recommendedBooks: List<Book> = emptyList()
    ): RecommendationResultsState
    data object Loading: RecommendationResultsState
    data class Error(
        val errorMessage: String
    ): RecommendationResultsState
}
