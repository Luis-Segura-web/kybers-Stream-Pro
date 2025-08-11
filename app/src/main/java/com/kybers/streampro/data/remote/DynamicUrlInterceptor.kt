package com.kybers.streampro.data.remote

import android.util.Log
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamicUrlInterceptor @Inject constructor() : Interceptor {
    
    @Volatile
    private var hostUrl: String? = null
    
    fun updateBaseUrl(host: String, port: String) {
        // Check if host already contains protocol to avoid duplication
        hostUrl = if (host.startsWith("http://") || host.startsWith("https://")) {
            "$host:$port/"
        } else {
            "http://$host:$port/"
        }
        Log.d("DynamicUrlInterceptor", "Base URL updated to: $hostUrl")
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // If we have a dynamic host URL, use it
        hostUrl?.let { newBaseUrl ->
            val newUrl = newBaseUrl.toHttpUrl().resolve(originalRequest.url.encodedPath + "?" + originalRequest.url.encodedQuery)
            if (newUrl != null) {
                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()
                Log.d("DynamicUrlInterceptor", "Redirecting request from ${originalRequest.url} to ${newRequest.url}")
                return chain.proceed(newRequest)
            }
        }
        
        // Otherwise, proceed with the original request
        Log.d("DynamicUrlInterceptor", "Using original URL: ${originalRequest.url}")
        return chain.proceed(originalRequest)
    }
}