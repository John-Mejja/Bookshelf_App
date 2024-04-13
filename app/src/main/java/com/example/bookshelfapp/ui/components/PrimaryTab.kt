package com.example.bookshelfapp.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.ui.screens.home.PagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedBooksPrimaryTab(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onSelectedTabIndexChange: (Int) -> Unit
){
    PrimaryScrollableTabRow(
        selectedTabIndex = pagerState.selectedTabIndex,
        edgePadding = dimensionResource(id = R.dimen.no_padding),
        indicator = { tabPosition ->
            val width by animateDpAsState(targetValue = tabPosition[pagerState.selectedTabIndex].contentWidth, label = "primary_top_bar_width")
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPosition[pagerState.selectedTabIndex]),
                width = width,
            )
        },
        divider = {},
        modifier = modifier
    ) {
        pagerState.recommendedCollections.forEachIndexed { index, recommendedCollection ->
            Tab(
                selected = pagerState.selectedTabIndex == index,
                onClick = {
                    onSelectedTabIndexChange(index)
                          },
                text = {
                    Text(
                        text = recommendedCollection,
                        color = if (pagerState.selectedTabIndex == index)
                            MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedBooksPrimaryTabPreview(){
    RecommendedBooksPrimaryTab(
        pagerState = PagerState(),
        onSelectedTabIndexChange = {}
    )
}