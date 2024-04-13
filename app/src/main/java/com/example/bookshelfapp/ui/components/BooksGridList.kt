@file:OptIn(ExperimentalLayoutApi::class)

package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BooksGridListCompact(
    modifier: Modifier = Modifier,
    books: List<Book>,
    onBookFavoriteIconClicked: (Book) -> Unit,
    onBookClicked: (String) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            maxItemsInEachRow = 3
        ) {
            books.forEach {book ->
                Surface(
                    tonalElevation = 3.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shadowElevation = 3.dp,
                    shape = RoundedCornerShape(3.dp),
                    modifier = Modifier
                        .weight(1f)

                ){
                    AddRowItem(
                        book = book,
                        onBookFavoriteIconClicked = onBookFavoriteIconClicked,
                        modifier = Modifier
                            .clickable {
                                onBookClicked(book.id)
                            }
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                        bookCoverModifier = Modifier
                            .width(100.dp)
                            .aspectRatio(.7f)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}
@Composable
fun AddRowItem(
    modifier: Modifier = Modifier,
    book: Book,
    onBookFavoriteIconClicked: (Book) -> Unit,
    bookCoverModifier: Modifier
) {
    BookDetails(
        book = book,
        onBookFavoriteIconClicked = onBookFavoriteIconClicked,
        bookCoverModifier = bookCoverModifier,
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_small))
    )
}


@Preview(showBackground = true)
@Composable
fun BooksGridListCompatPreview() {
    val mockBooks = List (10) {Book(title = "Book Title. A very very very very looooooong title")}
    BooksGridListCompact(
        books = mockBooks,
        onBookFavoriteIconClicked = {},
        onBookClicked = {}
    )
}
@Preview(showBackground = true)
@Composable
fun AddRowItemPreview(){
    val mockBook = Book(title = "Book Title")
    AddRowItem(
        book = mockBook,
        onBookFavoriteIconClicked = {},
        bookCoverModifier = Modifier
    )
}
