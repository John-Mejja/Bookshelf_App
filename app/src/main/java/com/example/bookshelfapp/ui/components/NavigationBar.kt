package com.example.bookshelfapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BottomNavigationScreens

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<BottomNavigationScreens>
) {

    NavigationBar(
        modifier = modifier
    ) {
        screens.forEach { screen ->
            AddBottomNavigationItem(
                screen = screen,
                currentDestination = currentDestination,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun RowScope.AddBottomNavigationItem(
    modifier: Modifier = Modifier,
    screen : BottomNavigationScreens,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(
                text = stringResource(id = screen.title)
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
                  navHostController.navigate(screen.route) {
                      popUpTo(navHostController.graph.findStartDestination().id )
                      launchSingleTop = true
                  }
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation icon"
            )
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit,
    currentDestination: NavDestination?,
    @StringRes title: Int,
    scrollBehavior: TopAppBarScrollBehavior
) {
    MediumTopAppBar(
        title = { 
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            if ( currentDestination?.hierarchy?.any {it.route == BottomNavigationScreens.Details.route} == true) {
                Icon(
                    imageVector = BottomNavigationScreens.Details.icon,
                    contentDescription = stringResource(id = R.string.back_icon_content_description),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.favorite_icon_size))
                        .clickable {
                            onBackIconClicked()
                        }
                )
            } else if (currentDestination?.hierarchy?.any {it.route == BottomNavigationScreens.Home.route} == true) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu_icon_content_description),
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.padding_small))
                )
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val mockNavHost = rememberNavController()
    val navBackEntry by mockNavHost.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val mockScreens = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Query,
        BottomNavigationScreens.Favorite
    )
    BottomNavigationBar(
        navHostController = mockNavHost,
        currentDestination = currentDestination,
        screens = mockScreens
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BookshelfTopAppBarPreview() {
    val mockNavHost = rememberNavController()
    val navBackEntry by mockNavHost.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    BookshelfTopAppBar(
        onBackIconClicked = {},
        currentDestination = currentDestination,
        title = R.string.detail_screen_title,
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

