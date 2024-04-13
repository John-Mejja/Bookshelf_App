package com.example.bookshelfapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelfapp.model.BottomNavigationScreens
import com.example.bookshelfapp.ui.components.BookshelfTopAppBar
import com.example.bookshelfapp.ui.components.BottomNavigationBar
import com.example.bookshelfapp.ui.screens.navigation.BottomBarNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(
    modifier: Modifier = Modifier
) {
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Query,
        BottomNavigationScreens.Favorite
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentDestination?.hierarchy?.any{ it.route == BottomNavigationScreens.Query.route} == false){
                BookshelfTopAppBar(
                    onBackIconClicked = navHostController::navigateUp,
                    currentDestination = currentDestination,
                    title = currentTitle(currentDestination),
                    scrollBehavior = scrollBehavior
                )
            }
        },
        bottomBar = {
            if (currentDestination?.hierarchy?.any{ it.route == BottomNavigationScreens.Details.route} == false){
                BottomNavigationBar(
                    navHostController = navHostController,
                    currentDestination = currentDestination,
                    screens = screens
                )
            }
        }
    ) {
        BottomBarNavGraph(
            navHostController = navHostController,
            contentPadding = it
        )
    }
}

private fun currentTitle( currentDestination: NavDestination?): Int {
    val title = currentDestination?.route?.let { route ->
        when (route) {
            BottomNavigationScreens.Home.route -> BottomNavigationScreens.Home.title
            BottomNavigationScreens.Query.route -> BottomNavigationScreens.Query.title
            BottomNavigationScreens.Favorite.route -> BottomNavigationScreens.Favorite.title
            BottomNavigationScreens.Details.route -> BottomNavigationScreens.Details.title
            else -> BottomNavigationScreens.Home.title

        }
    }
    return title ?: BottomNavigationScreens.Home.title
}