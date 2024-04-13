package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.FavoriteType
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    book: Book,
    onBookFavoriteIconClicked: (Book) -> Unit,
    bookCoverModifier: Modifier
    ){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        Box(
            modifier = Modifier

        ) {
            BookCoverImage(
                bookThumbnailUrl = book.thumbnailUrl,
                modifier = bookCoverModifier
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .clickable {
                        onBookFavoriteIconClicked(book)
                    }
            ){
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = stringResource(id = R.string.favorite_icon_content_description),
                    tint = if (book.favoriteType == FavoriteType.FAVORITE) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
        Text(
            text = book.title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(IntrinsicSize.Min)
        )
    }
}
@Composable
fun BookAuthor(
    modifier: Modifier = Modifier,
    bookAuthor: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(id = R.string.by)
        )
        Text(
            text = bookAuthor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailsPreview() {
    val mockBook = Book(title = "Book Title ")
    BookshelfAppTheme {
        BookDetails(
            book = mockBook,
            onBookFavoriteIconClicked = {},
            bookCoverModifier = Modifier

        )
    }
}
@Preview(showBackground = true)
@Composable
fun BookAuthorPreview() {
    BookAuthor(
        bookAuthor = "Book Author" )
}