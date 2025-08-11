package com.kybers.streampro.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@UnstableApi
@Composable
fun PlayerScreen(
    streamUrl: String,
    streamName: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    val exoPlayer = remember(streamUrl) {
        ExoPlayer.Builder(context)
            .build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(streamUrl)
                    .setMimeType(MimeTypes.APPLICATION_M3U8)
                    .build()
                setMediaItem(mediaItem)
                playWhenReady = true
                prepare()
            }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    useController = true
                    player = exoPlayer
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                }
            }
        )

        // Stream name overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = streamName,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}