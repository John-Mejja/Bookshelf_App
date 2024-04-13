package com.example.bookshelfapp.ui.screens.detail

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.ui.AppViewModelProvider
import com.example.bookshelfapp.ui.components.ConnectionError
import com.example.bookshelfapp.ui.components.LoadingScreen

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.detailUiState.collectAsState()
    val detailResultsState = uiState.detailResultsState

    if (uiState.isBookDetailQueryStarted) {
        when (detailResultsState) {
            is DetailResultsState.Success -> {
                BookFullDetails(
                    bookDetail = detailResultsState.bookDetail,
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                )
            }
            is DetailResultsState.Error -> {
                ConnectionError(
                    errorMessage = detailResultsState.errorMessage,
                    onRetryButtonClick = {
                     viewModel.updateIsBookDetailQueryStarted(false)
                     viewModel.fetchBook()
                    },
                    modifier = modifier
                )
            }
            is DetailResultsState.Loading -> {
                LoadingScreen(
                    modifier = modifier
                )
            }
        }
    } else {
        LoadingScreen(modifier = modifier)
    }
}
