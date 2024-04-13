package com.example.bookshelfapp.ui.screens.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.ui.components.BookCoverImage
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun BookFullDetails(
    modifier: Modifier = Modifier,
    bookDetail: Book
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        BookCoverInformation(
            bookTitle = bookDetail.title,
            bookAuthor = bookDetail.author,
            bookLargeThumbnailUrl = bookDetail.thumbnailUrl,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        HorizontalDivider()
        DetailInformation(
            informationLabel = R.string.about_the_book,
            informationDescription = bookDetail.description
        )
        HorizontalDivider()
        DetailInformation(
            informationLabel = R.string.authors,
            informationDescription = bookDetail.authors
        )
        HorizontalDivider()
        DetailInformation(
            informationLabel = R.string.publisher,
            informationDescription = bookDetail.publisher
        )
        HorizontalDivider()
        DetailInformation(
            informationLabel = R.string.published_date,
            informationDescription = bookDetail.publishedDate.toString()
        )
        HorizontalDivider()
        DetailInformation(
            informationLabel = R.string.buy_link,
            informationDescription = bookDetail.buyLink
        )
    }
}
@Composable
private fun BookCoverInformation(
    modifier: Modifier = Modifier,
    bookTitle: String,
    bookAuthor: String,
    bookLargeThumbnailUrl: String
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        BookCoverImage(
            bookThumbnailUrl = bookLargeThumbnailUrl,
            modifier = Modifier
                .width(300.dp)
                .aspectRatio(.7f)
        )
        CoverInformation(
            bookTitle = bookTitle,
            bookAuthor = bookAuthor
        )

    }
}
@Composable
private fun CoverInformation(
    modifier: Modifier = Modifier,
    bookTitle: String,
    bookAuthor: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = bookTitle,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = bookAuthor,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
@Composable
fun DetailInformation(
    modifier: Modifier = Modifier,
    @StringRes informationLabel: Int,
    informationDescription: String
){
    Column(
        modifier = modifier
    ){
        Text(
            text = stringResource(informationLabel),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_small))
        )
        Text(
            text = informationDescription,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BookFullDetailPreview() {
    val mockBook = Book(
        title = "Book Title",
        description = "This is a book description"
    )
    BookshelfAppTheme {
        BookFullDetails(bookDetail = mockBook)
    }
}
@Preview(showBackground = true)
@Composable
fun BookCoverInformationPreview() {
    BookCoverInformation(
        bookTitle = "Book Title",
        bookAuthor = "Book Author",
        bookLargeThumbnailUrl = ""
    )
}
@Preview(showBackground = true)
@Composable
fun DetailInformationPreview() {
    DetailInformation(
        informationLabel = R.string.about_the_book,
        informationDescription = "This is a book description.")
}