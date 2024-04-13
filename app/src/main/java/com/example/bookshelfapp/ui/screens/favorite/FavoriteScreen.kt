package com.example.bookshelfapp.ui.screens.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bookshelfapp.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navHostController: NavHostController
) {
    val uiState by viewModel.favoriteUiState.collectAsState()
    val favoriteBooks = uiState.favoriteBooks

    val coroutine = rememberCoroutineScope()
    if (favoriteBooks.isEmpty()) {
        EmptyFavoriteScreen(
            modifier = modifier
        )
    } else {
        FavoriteDetails(
            favoriteBooks = favoriteBooks,
            onBookFavoriteIconClicked = {
                coroutine.launch {
                    viewModel.updateFavoriteBook(it)
                }
            },
            modifier = modifier,
            onFavoriteBookClicked = {
                navHostController.navigate("details_screen/$it")
            }
        )
    }

}
@Composable
fun EmptyFavoriteScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Add favorite books to view them here",
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}