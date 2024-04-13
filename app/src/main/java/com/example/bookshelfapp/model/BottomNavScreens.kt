package com.example.bookshelfapp.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bookshelfapp.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    data object Home: BottomNavigationScreens(
        route = "home_screen",
        title = R.string.home_screen_title,
        icon = Icons.Default.Home
    )
    data object Query: BottomNavigationScreens(
        route = "query_screen",
        title = R.string.query_screen_title,
        icon = Icons.Default.Search
    )
    data object Favorite: BottomNavigationScreens(
        route = "favorite_screen",
        title = R.string.favorite_screen_title,
        icon = Icons.Default.Favorite
    )
    data object Details: BottomNavigationScreens (
        route = "details_screen/{$BOOK_ID_ARG}",
        title = R.string.detail_screen_title,
        icon = Icons.AutoMirrored.Default.ArrowBack
    )
}
const val BOOK_ID_ARG = "book_id"