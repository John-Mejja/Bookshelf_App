package com.example.bookshelfapp.ui.screens.query

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.extractBooksIds
import com.example.bookshelfapp.ui.AppViewModelProvider
import com.example.bookshelfapp.ui.screens.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

@Composable
fun QueryScreen(
    modifier: Modifier = Modifier,
    queryViewModel: QueryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navHostController: NavHostController,
    favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by queryViewModel.queryUiState.collectAsState()
    val searchState = uiState.searchState
    val queryResultsUiState = uiState.queryResultsUiState

    val favoriteUiState by favoriteViewModel.favoriteUiState.collectAsState()

    val coroutine = rememberCoroutineScope()
    QueryDetails(
        onQueryResultFavoriteIconClick = {
            coroutine.launch {
                favoriteViewModel.updateFavoriteBook(it)
            }
        },
        onQueryResultBookClick = { bookId ->
            navHostController.navigate("details_screen/$bookId")
        },
        onQueryChange = queryViewModel::updateSearchQuery,
        onSearch = { query ->
            queryViewModel.updateSearchActive(false)
            queryViewModel.fetchRecommendedBooks(query)
        },
        onActiveChange = queryViewModel::updateSearchActive,
        onBackSearchBarClicked = queryViewModel::resetSearchState,
        onClearSearchBarClicked = queryViewModel::clearSearch,
        searchUiState = searchState,
        queryResultsUiState = queryResultsUiState,
        isBookQuerySearchStarted = uiState.isBookQuerySearchStarted,
        favoriteBooksIds = favoriteUiState.favoriteBooks.extractBooksIds(),
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_large))
    )
}