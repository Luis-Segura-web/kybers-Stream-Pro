package com.kybers.streampro.media

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StreamMediaService : MediaSessionService() {
    
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        // TODO: Initialize ExoPlayer and MediaSession
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}