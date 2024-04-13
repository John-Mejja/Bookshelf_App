package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun NothingToShow(
    modifier: Modifier = Modifier,
    slotMessage: String
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = slotMessage,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(.5f)
        )
    }
}
@Composable
fun ConnectionError(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetryButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = errorMessage,
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.padding_large)
                )
        )
        Button(
            onClick = onRetryButtonClick
        ) {
            Text(
                text = stringResource(id = R.string.retry)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NothingToShowPreview() {
    NothingToShow(
        slotMessage = "No books added yet",
        modifier = Modifier
            .fillMaxSize()
    )
}
@Preview(showBackground = true)
@Composable
fun ConnectionErrorPreview() {
    BookshelfAppTheme {
        ConnectionError(
            errorMessage = "Network Error",
            onRetryButtonClick = {}
        )
    }
}