package com.example.bookshelfapp.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.FavoriteType
import com.example.bookshelfapp.ui.components.BookDetails
import com.example.bookshelfapp.ui.components.BooksGridListCompact
import com.example.bookshelfapp.ui.components.ConnectionError
import com.example.bookshelfapp.ui.components.LoadingScreen
import com.example.bookshelfapp.ui.components.NothingToShow
import com.example.bookshelfapp.ui.components.RecommendedBooksPrimaryTab
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun HomeDetails(
    modifier: Modifier = Modifier,
    favoriteBooks: List<Book>,
    onSelectedTabIndexChange: (Int) -> Unit,
    onBookClicked: (String) -> Unit,
    onRecommendationFavoriteIconClicked: (Book) -> Unit,
    onFavoriteBookFavoriteIconClicked: (Book) -> Unit,
    recommendationUiState: RecommendationUiState,
    onRetryButtonClicked: () -> Unit,
    favoriteBooksIds: List<String>

) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
    ) {

        FavoriteBooks(
            favoriteBooks = favoriteBooks,
            onFavoriteBookFavoriteIconClicked = onFavoriteBookFavoriteIconClicked,
            onFavoriteBookClicked = onBookClicked
        )
        HorizontalDivider()
        RecommendedBooksResultsItem(
            onSelectedTabIndexChange = onSelectedTabIndexChange,
            onRecommendedBookClicked = onBookClicked,
            onRecommendationFavoriteIconClicked = onRecommendationFavoriteIconClicked,
            recommendationUiState = recommendationUiState,
            onRetryButtonClicked = onRetryButtonClicked,
            favoriteBooksIds = favoriteBooksIds
        )
        HorizontalDivider()
    }
}
@Composable
fun FavoriteBooks(
    modifier: Modifier = Modifier,
    favoriteBooks: List<Book>,
    onFavoriteBookFavoriteIconClicked: (Book) -> Unit,
    onFavoriteBookClicked :(String) -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ){
        Text(
            text = "Your Library",
            style = MaterialTheme.typography.titleMedium
        )
        if (favoriteBooks.isEmpty()) {
            NothingToShow(
                slotMessage = "No favorite books added.",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp))
        } else {
            FavoriteBooksResults(
                favoriteBooks = favoriteBooks,
                onFavoriteBookFavoriteIconClicked = onFavoriteBookFavoriteIconClicked,
                onFavoriteBookClicked = onFavoriteBookClicked
            )
        }
    }
}
@Composable
fun FavoriteBooksResults(
    modifier: Modifier = Modifier,
    favoriteBooks: List<Book>,
    onFavoriteBookFavoriteIconClicked: (Book) -> Unit,
    onFavoriteBookClicked: (String) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        items(items = favoriteBooks, key = {favoriteBook -> favoriteBook.id}) {favoriteBook ->
            FavoriteBook(
                favoriteBook = favoriteBook,
                onFavoriteBookFavoriteIconClicked = onFavoriteBookFavoriteIconClicked,
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        onFavoriteBookClicked(favoriteBook.id)
                    }
            )

        }
    }
}

@Composable
fun FavoriteBook(
    modifier: Modifier = Modifier,
    favoriteBook: Book,
    onFavoriteBookFavoriteIconClicked :(Book) -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.favorite_rounded_corner_size)),
        shadowElevation = dimensionResource(id = R.dimen.elevation_level_1),
        tonalElevation = dimensionResource(id = R.dimen.elevation_level_2)
    ) {
        BookDetails(
            book = favoriteBook,
            onBookFavoriteIconClicked = onFavoriteBookFavoriteIconClicked,
            bookCoverModifier = Modifier
                .height(dimensionResource(id = R.dimen.favorite_book_cover_height))
            ,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
        )

    }
}
@Composable
fun RecommendedBooksResultsItem(
    modifier: Modifier = Modifier,
    onSelectedTabIndexChange: (Int) -> Unit,
    onRecommendedBookClicked: (String) -> Unit,
    onRecommendationFavoriteIconClicked: (Book) -> Unit,
    recommendationUiState: RecommendationUiState,
    onRetryButtonClicked: () -> Unit,
    favoriteBooksIds: List<String>
){

    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Recommendation",
            style = MaterialTheme.typography.titleMedium
        )
        RecommendedBooksPrimaryTab(
            pagerState = recommendationUiState.pagerState,
            onSelectedTabIndexChange = onSelectedTabIndexChange,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        if (recommendationUiState.isRecommendedBooksSearching){
            val recommendationResultsState = recommendationUiState.recommendationResultsState
            when (recommendationResultsState) {
                is RecommendationResultsState.Success -> {
                    RecommendedBooksGrid(
                        recommendedBooks = recommendationResultsState.recommendedBooks,
                        onRecommendationFavoriteIconClicked = onRecommendationFavoriteIconClicked,
                        onRecommendedBookClicked = onRecommendedBookClicked,
                        favoriteBooksIds = favoriteBooksIds
                    )
                }
                is RecommendationResultsState.Error -> {
                    ConnectionError(
                        errorMessage = recommendationResultsState.errorMessage,
                        onRetryButtonClick = onRetryButtonClicked
                    )
                }
                is RecommendationResultsState.Loading -> {
                    LoadingScreen()
                }
            }
        } else {
            LoadingScreen()
        }

    }
}
@Composable
fun RecommendedBooksGrid(
    modifier: Modifier = Modifier,
    recommendedBooks : List<Book>,
    onRecommendationFavoriteIconClicked: (Book) -> Unit,
    onRecommendedBookClicked: (String) -> Unit,
    favoriteBooksIds: List<String>
) {
    recommendedBooks.forEach {
        if (it.id in favoriteBooksIds) {
            it.favoriteType = FavoriteType.FAVORITE
        } else {
            it.favoriteType = FavoriteType.NOT_FAVORITE
        }
    }
    BooksGridListCompact(
        books = recommendedBooks,
        onBookFavoriteIconClicked = onRecommendationFavoriteIconClicked,
        onBookClicked = onRecommendedBookClicked,
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun HomeDetailsPreview(){
    BookshelfAppTheme {
        val mockFavoriteBooks = List(4) { Book( id = it.toString(), title = "Book Title")}
        val mockBooks = List(10) { Book( id = it.toString(), title = "Book Title")}
        HomeDetails(
            favoriteBooks = mockFavoriteBooks,
            onSelectedTabIndexChange = {},
            onRecommendationFavoriteIconClicked = {},
            onBookClicked = {},
            onFavoriteBookFavoriteIconClicked = {},
            recommendationUiState = RecommendationUiState(
                recommendationResultsState = RecommendationResultsState.Success(mockBooks),
                isRecommendedBooksSearching = true
            ),
            onRetryButtonClicked = {},
            favoriteBooksIds = emptyList()
        )
    }
}
//@Preview( showBackground = true)
//@Composable
//fun FavoriteBooksPreview(){
//    val mockBooks = List(4) { Book( id = it.toString(), title = "Book Title")}
//    FavoriteBooks(
//        favoriteBooks = mockBooks,
//        onFavoriteBookClicked = {},
//        onFavoriteBookFavoriteIconClicked = {}
//    )
//}
//@Preview( showBackground = true)
//@Composable
//fun FavoriteBookPreview(){
//    val mockBook = Book( title = "Book Title")
//    FavoriteBook(
//        favoriteBook = mockBook,
//        onFavoriteBookFavoriteIconClicked = {}
//    )
//}
//@Preview(showBackground = true)
//@Composable
//fun RecommendedBooksGridPreview(){
//    val mockBooks = List(10) { Book( id = it.toString(), title = "Book long very very long Title")}
//    RecommendedBooksGrid(
//        recommendedBooks = mockBooks ,
//        onRecommendationFavoriteIconClicked = {},
//        onRecommendedBookClicked = {},
//        favoriteBooksIds = emptyList()
//    )
//}
//@Preview( showBackground = true)
//@Composable
//fun RecommendedBooksResultsItemPreview(){
//    val mockBooks = List(10) { Book( id = it.toString(), title = "Book long very very long Title")}
//    RecommendedBooksResultsItem(
//        onSelectedTabIndexChange = {},
//        onRecommendedBookClicked = {},
//        onRecommendationFavoriteIconClicked = {},
//        recommendationUiState = RecommendationUiState(
//            isRecommendedBooksSearching = true,
//            recommendationResultsState = RecommendationResultsState.Success(mockBooks)
//        ),
//        onRetryButtonClicked = {}
//    )
//}
//@Preview( showBackground = true)
//@Composable
//fun RecommendedBookDetailsPreview(){
//    RecommendedBookDetails(
//        recommendedBook = Book(title = "Book Title"),
//        onRecommendationFavoriteIconClicked = {}
//    )
//}
