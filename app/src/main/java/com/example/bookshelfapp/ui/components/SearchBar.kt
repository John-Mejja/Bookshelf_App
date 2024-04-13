package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.screens.home.SearchState
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchComponent(
    modifier: Modifier = Modifier,
    onQueryChange :(String) -> Unit,
    onSearch :(String) -> Unit,
    onActiveChange :(Boolean) -> Unit,
    onBackSearchBarClicked :() -> Unit,
    onClearSearchBarClicked: () -> Unit,
    searchUiState: SearchState

) {
    SearchBar(
        query = searchUiState.query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = searchUiState.active,
        shape = SearchBarDefaults.fullScreenShape,
        onActiveChange = onActiveChange,
        modifier = modifier,
        leadingIcon = {
            if (!searchUiState.active && searchUiState.query.isBlank()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                        .clickable {
                            onBackSearchBarClicked()
                        }
                )
            }
        },
        placeholder = {
            if (searchUiState.query.isBlank()){
                Text(text = "Search collections eg. 'Computer'")
            }
        },
        trailingIcon = {
            if (searchUiState.query.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.search_clear_icon_content_description),
                    modifier = Modifier
                        .clickable {
                            onClearSearchBarClicked()
                        }
                )
            }
        }
    ) {
        searchUiState.searchHistories.forEach { searchHistory ->
            SearchHistoryDetails(
                searchHistory = searchHistory
            )
        }
    }
}
@Composable
private fun SearchHistoryDetails(
    modifier: Modifier = Modifier,
    searchHistory: String
){
    Row(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_history),
            contentDescription = null,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.padding_medium))
        )
        Text(
            text = searchHistory,
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen.padding_small))
        )
    }
}
@Preview( showBackground = true)
@Composable
fun BookSearchComponentPreview(){
    BookshelfAppTheme {
        BookSearchComponent(
            searchUiState = SearchState(),
            onQueryChange = {},
            onSearch = {},
            onActiveChange ={},
            onBackSearchBarClicked = {},
            onClearSearchBarClicked = {}
        )
    }
}

@Preview( showBackground = true)
@Composable
fun SearchHistoryDetailsPreview(){
    BookshelfAppTheme {
        SearchHistoryDetails(searchHistory = "Computer")
    }
}