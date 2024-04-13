package com.example.bookshelfapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.R

@Composable
fun BookCoverImage(
    modifier: Modifier = Modifier,
    bookThumbnailUrl : String,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(bookThumbnailUrl)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(id = R.string.book_thumbnail_content_description),
        contentScale = ContentScale.FillBounds,
        placeholder = painterResource(id = R.drawable.loading_image),
        error = painterResource(id = R.drawable.baseline_broken_image),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun BookCoverImagePreview() {
    BookCoverImage(
        bookThumbnailUrl = ""
    )
}