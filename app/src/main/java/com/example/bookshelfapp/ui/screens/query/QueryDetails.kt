package com.example.bookshelfapp.ui.screens.query

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.FavoriteType
import com.example.bookshelfapp.ui.components.BookAuthor
import com.example.bookshelfapp.ui.components.BookCoverImage
import com.example.bookshelfapp.ui.components.BookSearchComponent
import com.example.bookshelfapp.ui.components.ConnectionError
import com.example.bookshelfapp.ui.components.LoadingScreen
import com.example.bookshelfapp.ui.screens.home.SearchState

@Composable
fun QueryDetails(
    modifier: Modifier = Modifier,
    onQueryResultFavoriteIconClick: (Book) -> Unit,
    onQueryResultBookClick :(String) -> Unit,
    onQueryChange :(String) -> Unit,
    onSearch :(String) -> Unit,
    onActiveChange :(Boolean) -> Unit,
    onBackSearchBarClicked :() -> Unit,
    onClearSearchBarClicked: () -> Unit,
    searchUiState: SearchState,
    isBookQuerySearchStarted: Boolean,
    queryResultsUiState: QueryResultsUiState,
    favoriteBooksIds: List<String>
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        BookSearchComponent(
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            onActiveChange = onActiveChange,
            onBackSearchBarClicked = onBackSearchBarClicked,
            onClearSearchBarClicked = onClearSearchBarClicked,
            searchUiState = searchUiState,
            modifier = Modifier
                .fillMaxWidth()
        )
        if (isBookQuerySearchStarted) {
            when (queryResultsUiState){
                is QueryResultsUiState.Success -> {
                    QueryResults(
                        booksResult = queryResultsUiState.queryBooksResults ,
                        onQueryResultFavoriteIconClick = onQueryResultFavoriteIconClick,
                        onQueryResultBookClick = onQueryResultBookClick,
                        favoriteBooksIds = favoriteBooksIds
                    )
                }
                is QueryResultsUiState.Error -> {
                    ConnectionError(
                        errorMessage = queryResultsUiState.errorMessage,
                        onRetryButtonClick = {}
                    )
                }
                is QueryResultsUiState.Loading -> {
                    LoadingScreen()
                }
            }
        } else {
            LoadingScreen()
        }
    }
}

@Composable
fun QueryResults(
    modifier: Modifier = Modifier,
    booksResult: List<Book>,
    onQueryResultFavoriteIconClick: (Book) -> Unit,
    onQueryResultBookClick :(String) -> Unit,
    favoriteBooksIds: List<String>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        item {
            Text(
                text = stringResource(id = R.string.search_results),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        booksResult.forEach {
            if (it.id in favoriteBooksIds) {
                it.favoriteType = FavoriteType.FAVORITE
            } else {
                it.favoriteType = FavoriteType.NOT_FAVORITE
            }
        }
        itemsIndexed(items = booksResult) {index, bookResult ->
            Surface(
                shadowElevation = dimensionResource(id = R.dimen.elevation_level_2),
                color = MaterialTheme.colorScheme.secondary
            ){
                QueryResult(
                    bookResult = bookResult,
                    onQueryResultFavoriteIconClick = onQueryResultFavoriteIconClick,
                    modifier = Modifier
                        .clickable {
                            onQueryResultBookClick(bookResult.id)
                        }
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            if (index < booksResult.size) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun QueryResult(
    modifier: Modifier = Modifier,
    bookResult: Book,
    onQueryResultFavoriteIconClick :(Book) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        modifier = modifier
    ) {
        BookCoverImage(
            bookThumbnailUrl = bookResult.thumbnailUrl,
            modifier = Modifier
                .fillMaxWidth(.3f)
                .aspectRatio(.8f)
        )
        QueryBookDetails(
            bookTitle = bookResult.title,
            bookAuthor = bookResult.author,
            modifier = Modifier
                .weight(1f)
        )
        Surface(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clickable {
                    onQueryResultFavoriteIconClick(bookResult)
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = stringResource(id = R.string.favorite_icon_content_description),
                tint = if (bookResult.favoriteType == FavoriteType.FAVORITE) Color.Red else Color.Black,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.favorite_icon_size))
            )
        }
    }
}

@Composable
fun QueryBookDetails(
    modifier: Modifier = Modifier,
    bookTitle: String,
    bookAuthor: String
){
    Column (
        modifier = modifier
    ){
        Text(
            text = bookTitle,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
        BookAuthor(
            bookAuthor = bookAuthor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QueryDetailsPreview() {
    val mockBook = List(10) { Book(id = it.toString(),title = "Book Title", author = "Book Author") }
    QueryDetails(
        onQueryResultFavoriteIconClick = {},
        onQueryResultBookClick = {},
        searchUiState = SearchState(),
        onQueryChange = {},
        onSearch = {},
        onActiveChange ={},
        onBackSearchBarClicked = {},
        onClearSearchBarClicked = {},
        isBookQuerySearchStarted = true,
        queryResultsUiState = QueryResultsUiState.Success(mockBook),
        favoriteBooksIds = emptyList()

    )
}
@Preview(showBackground = true)
@Composable
fun QueryResultPreview() {
    val mockBook = Book(title = "Book Title", author = "Book Author")
    QueryResult(
        bookResult = mockBook,
        onQueryResultFavoriteIconClick = {}
    )
}
@Preview(showBackground = true)
@Composable
fun QueryBookDetailsPreview() {
    QueryBookDetails(
        bookTitle = "Book Title",
        bookAuthor = "John Major"
    )
}