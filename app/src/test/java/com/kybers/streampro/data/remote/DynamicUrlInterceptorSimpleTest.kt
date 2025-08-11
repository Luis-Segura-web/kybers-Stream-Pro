package com.kybers.streampro.data.remote

import org.junit.Test
import org.junit.Assert.*

/**
 * Test simple para verificar que DynamicUrlInterceptor 
 * maneja correctamente los hosts que ya incluyen el protocolo
 * Esto corrige el error del log donde aparecía "http://http://gzytv.vip:8880/"
 */
class DynamicUrlInterceptorSimpleTest {

    @Test
    fun `should not duplicate protocol when host already contains http`() {
        val interceptor = DynamicUrlInterceptor()
        
        // Simular el caso problemático del log
        val hostWithProtocol = "http://gzytv.vip"
        val port = "8880"
        
        interceptor.updateBaseUrl(hostWithProtocol, port)
        
        // Verificar usando reflexión que la URL no tiene protocolo duplicado
        val hostUrlField = DynamicUrlInterceptor::class.java.getDeclaredField("hostUrl")
        hostUrlField.isAccessible = true
        val actualHostUrl = hostUrlField.get(interceptor) as String?
        
        assertEquals("http://gzytv.vip:8880/", actualHostUrl)
        assertFalse("URL should not contain duplicate protocol", 
                   actualHostUrl?.contains("http://http://") == true)
    }

    @Test 
    fun `should add protocol when host does not contain it`() {
        val interceptor = DynamicUrlInterceptor()
        
        val hostWithoutProtocol = "gzytv.vip"
        val port = "8880"
        
        interceptor.updateBaseUrl(hostWithoutProtocol, port)
        
        val hostUrlField = DynamicUrlInterceptor::class.java.getDeclaredField("hostUrl")
        hostUrlField.isAccessible = true
        val actualHostUrl = hostUrlField.get(interceptor) as String?
        
        assertEquals("http://gzytv.vip:8880/", actualHostUrl)
    }

    @Test
    fun `should handle https protocol correctly`() {
        val interceptor = DynamicUrlInterceptor()
        
        val hostWithHttps = "https://secure.example.com"
        val port = "443"
        
        interceptor.updateBaseUrl(hostWithHttps, port)
        
        val hostUrlField = DynamicUrlInterceptor::class.java.getDeclaredField("hostUrl")
        hostUrlField.isAccessible = true
        val actualHostUrl = hostUrlField.get(interceptor) as String?
        
        assertEquals("https://secure.example.com:443/", actualHostUrl)
    }
}