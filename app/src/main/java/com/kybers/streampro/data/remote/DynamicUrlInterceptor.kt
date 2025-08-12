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
        // Normaliza y sanea host/puerto para evitar saltos de línea, espacios y puertos duplicados
        val rawInput = host.trim() // elimina espacios/saltos de línea al inicio/fin
        val hasHttps = rawInput.startsWith("https://", ignoreCase = true)
        val hasHttp = rawInput.startsWith("http://", ignoreCase = true)

        // Quita esquema y cualquier trailing slash
        var withoutScheme = rawInput
            .removePrefix("https://")
            .removePrefix("http://")
            .trim() // por si queda espacio tras quitar esquema
            .trimEnd('/')

        // Si el host incluye una ruta, quítala (nos quedamos con el hostname[:puerto])
        // p.ej. "gzytv.vip:8880/path" -> "gzytv.vip:8880"
        val slashIdx = withoutScheme.indexOf('/')
        if (slashIdx >= 0) {
            withoutScheme = withoutScheme.substring(0, slashIdx)
        }

        // Gestiona puerto: evita duplicarlo si ya viene en el host
        val hasPortInHost = withoutScheme.contains(":")
        val cleanPort = port.trim().removePrefix(":")
        val authority = if (hasPortInHost || cleanPort.isEmpty()) {
            withoutScheme
        } else {
            "$withoutScheme:$cleanPort"
        }

        // Determina esquema final
        val scheme = when {
            hasHttps -> "https://"
            hasHttp -> "http://"
            else -> "http://"
        }

        hostUrl = "$scheme$authority/" // asegura slash final
        Log.d("DynamicUrlInterceptor", "Base URL updated to: $hostUrl")
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // If we have a dynamic host URL, use it
        hostUrl?.let { newBaseUrl ->
            try {
                val baseUrl = newBaseUrl.toHttpUrl()
                val originalUrl = originalRequest.url

                // Properly construct the new URL with path and query parameters
                val newUrlBuilder = baseUrl.newBuilder()
                    .encodedPath(originalUrl.encodedPath)

                // Add query parameters if they exist
                originalUrl.queryParameterNames.forEach { paramName ->
                    originalUrl.queryParameterValues(paramName).forEach { paramValue ->
                        if (paramValue != null) {
                            newUrlBuilder.addQueryParameter(paramName, paramValue)
                        }
                    }
                }

                val newUrl = newUrlBuilder.build()
                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()
                Log.d("DynamicUrlInterceptor", "Redirecting request from ${originalRequest.url} to ${newRequest.url}")
                return chain.proceed(newRequest)
            } catch (e: IllegalArgumentException) {
                // Si la URL base es inválida, evita crashear y continúa con la original
                Log.e("DynamicUrlInterceptor", "Invalid base URL '$newBaseUrl'. Proceeding with original request.", e)
                return chain.proceed(originalRequest)
            }
        }
        
        // Otherwise, proceed with the original request
        Log.d("DynamicUrlInterceptor", "Using original URL: ${originalRequest.url}")
        return chain.proceed(originalRequest)
    }
}