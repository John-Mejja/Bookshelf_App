package com.example.bookshelfapp.ui.screens.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BOOK_ID_ARG
import com.example.bookshelfapp.model.BottomNavigationScreens
import com.example.bookshelfapp.ui.screens.detail.DetailScreen
import com.example.bookshelfapp.ui.screens.favorite.FavoriteScreen
import com.example.bookshelfapp.ui.screens.home.HomeScreen
import com.example.bookshelfapp.ui.screens.query.QueryScreen

@Composable
fun BottomBarNavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavigationScreens.Home.route,
        modifier = modifier
            .padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding(),
            )
    ) {
        composable(route = BottomNavigationScreens.Home.route) {
            HomeScreen(
                navHostController = navHostController,
                modifier  = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
            )
        }
        composable(route = BottomNavigationScreens.Query.route) {
            QueryScreen(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                navHostController = navHostController
            )
        }
        composable(route = BottomNavigationScreens.Favorite.route) {
            FavoriteScreen(
                modifier  = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                navHostController = navHostController
            )
        }
        composable(
            route = BottomNavigationScreens.Details.route,
            arguments = listOf(
                navArgument(name = BOOK_ID_ARG) {
                    type = NavType.StringType
                })
        ) {
            DetailScreen(
                modifier  = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
            )

        }
    }
}