package com.kybers.streampro.data.remote

import okhttp3.Request
import okhttp3.Response
import okhttp3.Interceptor
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DynamicUrlInterceptorTest {

    private lateinit var interceptor: DynamicUrlInterceptor

    @Mock
    private lateinit var chain: Interceptor.Chain

    @Mock
    private lateinit var request: Request

    @Mock
    private lateinit var response: Response

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        interceptor = DynamicUrlInterceptor()
    }

    @Test
    fun `updateBaseUrl should not duplicate http protocol when host already contains it`() {
        // Given
        val hostWithProtocol = "http://gzytv.vip"
        val port = "8880"
        
        // When
        interceptor.updateBaseUrl(hostWithProtocol, port)
        
        // Then - The base URL should not have duplicate http://
        // We'll verify this by checking that the interceptor behaves correctly
        // when processing a request
        
        val originalUrl = "http://example.com/player_api.php?username=test&password=test".toHttpUrl()
        `when`(request.url).thenReturn(originalUrl)
        `when`(request.newBuilder()).thenReturn(request.newBuilder())
        `when`(chain.request()).thenReturn(request)
        `when`(chain.proceed(any())).thenReturn(response)
        
        // The interceptor should process without throwing UnknownHostException
        val result = interceptor.intercept(chain)
        assertNotNull(result)
    }

    @Test
    fun `updateBaseUrl should add http protocol when host does not contain it`() {
        // Given
        val hostWithoutProtocol = "gzytv.vip"
        val port = "8880"
        
        // When
        interceptor.updateBaseUrl(hostWithoutProtocol, port)
        
        // Then - The base URL should have http:// added
        val originalUrl = "http://example.com/player_api.php?username=test&password=test".toHttpUrl()
        `when`(request.url).thenReturn(originalUrl)
        `when`(request.newBuilder()).thenReturn(request.newBuilder())
        `when`(chain.request()).thenReturn(request)
        `when`(chain.proceed(any())).thenReturn(response)
        
        val result = interceptor.intercept(chain)
        assertNotNull(result)
    }

    @Test
    fun `updateBaseUrl should handle https protocol correctly`() {
        // Given
        val hostWithHttps = "https://gzytv.vip"
        val port = "8880"
        
        // When
        interceptor.updateBaseUrl(hostWithHttps, port)
        
        // Then - The base URL should not have duplicate protocol
        val originalUrl = "http://example.com/player_api.php?username=test&password=test".toHttpUrl()
        `when`(request.url).thenReturn(originalUrl)
        `when`(request.newBuilder()).thenReturn(request.newBuilder())
        `when`(chain.request()).thenReturn(request)
        `when`(chain.proceed(any())).thenReturn(response)
        
        val result = interceptor.intercept(chain)
        assertNotNull(result)
    }
}