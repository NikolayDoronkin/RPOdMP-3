package com.wtfcompany.relax.view.guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.wtfcompany.relax.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GuideScreen(navController: NavHostController) {
    TabLayoutDemo()
}

private val tabs = listOf(
    TabItem.Home,
    TabItem.Settings,
    TabItem.Contacts
)

sealed class TabItem(
    val index: Int,
    val icon: ImageVector,
    val title: String,
    val screenToLoad: @Composable () -> Unit
) {
    object Home : TabItem(
        0,
        Icons.Default.Home,
        "Home", { HomeScreenForTab() }
    )

    object Contacts : TabItem(
        2,
        Icons.Default.Info,
        "Contacts", { MusicScreenForTab() }
    )

    object Settings : TabItem(
        1,
        Icons.Default.Settings,
        "Settings", { ProfileScreenForTab() }
    )
}

@Composable
fun HomeScreenForTab() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = stringResource(id = R.string.descript_home),
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(700.dp)
        )
        Text(
            text = stringResource(id = R.string.text_home_screen),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun MusicScreenForTab() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.music),
            contentDescription = stringResource(id = R.string.descript_music),
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(700.dp)
        )
        Text(
            text = stringResource(id = R.string.text_music_recommendation),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun ProfileScreenForTab() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = stringResource(id = R.string.descript_profile),
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(700.dp)
        )
        Text(
            text = "Profile screen, where you can upload images",
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@ExperimentalPagerApi
@Composable
fun TabLayoutDemo() {
    val pagerState = rememberPagerState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            TabPage(tabItems = tabs, pagerState = pagerState)
        }
    )
}

@ExperimentalPagerApi
@Composable
fun TabPage(pagerState: PagerState, tabItems: List<TabItem>) {
    HorizontalPager(
        count = tabs.size,
        state = pagerState
    ) { index ->
        tabItems[index].screenToLoad()
    }
}