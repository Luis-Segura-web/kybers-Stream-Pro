package com.kybers.streampro.ui

import androidx.compose.runtime.*
import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kybers.streampro.ui.login.LoginScreen
import com.kybers.streampro.ui.home.HomeScreen
import com.kybers.streampro.ui.player.PlayerScreen
import com.kybers.streampro.ui.navigation.NavigationViewModel
import com.kybers.streampro.ui.navigation.NavigationState
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.launch

@UnstableApi
@Composable
fun StreamProApp() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val credentialsState by navigationViewModel.credentialsState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    
    // Determine start destination based on login status
    val startDestination = when (val state = credentialsState) {
        is NavigationState.Loading -> "login"
        is NavigationState.Success -> if (state.credentials.isLoggedIn) "home" else "login"
        else -> "login"
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                onStreamSelected = { stream ->
                    val encodedName = Uri.encode(stream.name)
                    navController.navigate("player/${stream.streamId}/$encodedName")
                }
            )
        }
        
        composable(
            "player/{streamId}/{streamName}",
            arguments = listOf(
                navArgument("streamId") { type = NavType.IntType },
                navArgument("streamName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val streamId = backStackEntry.arguments?.getInt("streamId") ?: 0
            val streamName = backStackEntry.arguments?.getString("streamName")?.let { Uri.decode(it) } ?: ""
            
            var streamUrl by remember { mutableStateOf("") }
            
            LaunchedEffect(streamId) {
                coroutineScope.launch {
                    streamUrl = navigationViewModel.getStreamUrl(streamId)
                }
            }
            
            if (streamUrl.isNotEmpty()) {
                PlayerScreen(
                    streamUrl = streamUrl,
                    streamName = streamName,
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}