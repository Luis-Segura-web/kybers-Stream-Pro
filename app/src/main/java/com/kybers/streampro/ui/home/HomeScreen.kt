package com.kybers.streampro.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
// import com.kybers.streampro.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Kybers Stream Pro") } // stringResource(R.string.app_name)) }
        )

        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Live TV") }, // stringResource(R.string.live_tv)) },
                icon = { Icon(Icons.Default.LiveTv, contentDescription = null) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Movies") }, // stringResource(R.string.vod)) },
                icon = { Icon(Icons.Default.Movie, contentDescription = null) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Series") }, // stringResource(R.string.series)) },
                icon = { Icon(Icons.Default.Tv, contentDescription = null) }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text("Favorites") }, // stringResource(R.string.favorites)) },
                icon = { Icon(Icons.Default.Favorite, contentDescription = null) }
            )
        }

        when (selectedTab) {
            0 -> LiveTvContent()
            1 -> VodContent()
            2 -> SeriesContent()
            3 -> FavoritesContent()
        }
    }
}

@Composable
private fun LiveTvContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Live TV content will be implemented here")
    }
}

@Composable
private fun VodContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("VOD content will be implemented here")
    }
}

@Composable
private fun SeriesContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Series content will be implemented here")
    }
}

@Composable
private fun FavoritesContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Favorites content will be implemented here")
    }
}