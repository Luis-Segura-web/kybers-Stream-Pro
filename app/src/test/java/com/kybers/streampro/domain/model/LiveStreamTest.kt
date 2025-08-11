package com.kybers.streampro.domain.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LiveStreamTest {
    @Test
    fun getStreamUrl_returnsCorrectM3u8Url() {
        val stream = LiveStream(
            num = 1,
            name = "Test Channel",
            streamId = 12345,
            streamIcon = null,
            categoryId = "1",
            tvArchive = 0,
            directSource = null,
            tvArchiveUrl = null,
            epgChannelId = null,
            added = null
        )
        
        val url = stream.getStreamUrl("example.com", "80", "testuser", "testpass")
        assertEquals("http://example.com:80/live/testuser/testpass/12345.m3u8", url)
    }
    
    @Test
    fun getTsStreamUrl_returnsCorrectTsUrl() {
        val stream = LiveStream(
            num = 1,
            name = "Test Channel",
            streamId = 12345,
            streamIcon = null,
            categoryId = "1",
            tvArchive = 0,
            directSource = null,
            tvArchiveUrl = null,
            epgChannelId = null,
            added = null
        )
        
        val url = stream.getTsStreamUrl("example.com", "80", "testuser", "testpass")
        assertEquals("http://example.com:80/live/testuser/testpass/12345.ts", url)
    }
}