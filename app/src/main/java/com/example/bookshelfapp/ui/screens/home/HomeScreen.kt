package com.example.bookshelfapp.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bookshelfapp.model.extractBooksIds
import com.example.bookshelfapp.ui.AppViewModelProvider
import com.example.bookshelfapp.ui.screens.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navHostController: NavHostController,
    favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by  viewModel.homeUiState.collectAsState()
    val recommendationUiState = uiState.recommendationUiState

    val favoriteUiState by favoriteViewModel.favoriteUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    HomeDetails(
        favoriteBooks = favoriteUiState.favoriteBooks,
        onSelectedTabIndexChange = {
            viewModel.updateIsRecommendedBooksSearching(false)
            viewModel.updateSelectedTabIndex(it)
            viewModel.fetchRecommendedBooks()
        },
        onBookClicked = {bookId ->
            navHostController.navigate("details_screen/$bookId")
        },
        onRecommendationFavoriteIconClicked = {
            coroutineScope.launch {
                favoriteViewModel.updateFavoriteBook(it)
            }
        },
        onFavoriteBookFavoriteIconClicked = {
            coroutineScope.launch {
              favoriteViewModel.updateFavoriteBook(it)
            }
        },
        recommendationUiState = recommendationUiState,
        modifier = modifier,
        onRetryButtonClicked = viewModel::fetchRecommendedBooks,
        favoriteBooksIds = favoriteUiState.favoriteBooks.extractBooksIds()
    )
}