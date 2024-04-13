package com.example.bookshelfapp.ui.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.ui.components.BookDetails

@Composable
fun FavoriteDetails(
    modifier: Modifier = Modifier,
    favoriteBooks: List<Book>,
    onBookFavoriteIconClicked: (Book) -> Unit,
    onFavoriteBookClicked: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            items(
                items = favoriteBooks,
                key = { favoriteBook -> favoriteBook.id }) { favoriteBook ->
                Surface(
                    color = MaterialTheme.colorScheme.secondary
                ){
                    FavoriteDetail(
                        favoriteBook = favoriteBook,
                        onBookFavoriteIconClicked = onBookFavoriteIconClicked,
                        modifier = Modifier
                            .clickable { onFavoriteBookClicked(favoriteBook.id) }
                            .padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
        }
    }
}
@Composable
fun FavoriteDetail(
    modifier: Modifier = Modifier,
    favoriteBook: Book,
    onBookFavoriteIconClicked: (Book) -> Unit
) {
    BookDetails(
        book = favoriteBook,
        onBookFavoriteIconClicked = onBookFavoriteIconClicked,
        bookCoverModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(.7f)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.elevation_level_2))),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteDetailsPreview() {
    val mockBooks = List(10) {
        Book(
        id = it.toString(),
        title = "Book Title",
        author = "Daniel"
    )
    }
    FavoriteDetails(
        favoriteBooks = mockBooks,
        onBookFavoriteIconClicked = {},
        onFavoriteBookClicked = {}
    )
}
@Preview(showBackground = true)
@Composable
fun FavoriteDetailPreview() {
    val mockBook = Book()
    FavoriteDetail(
        favoriteBook = mockBook,
        onBookFavoriteIconClicked = {}
    )
}