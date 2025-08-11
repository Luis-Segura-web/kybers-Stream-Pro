package com.kybers.streampro.data.remote

import com.kybers.streampro.data.remote.dto.CategoryDto
import com.kybers.streampro.data.remote.dto.LiveStreamDto
import com.kybers.streampro.data.remote.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface XtreamApi {
    
    @GET("player_api.php")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponseDto>
    
    @GET("player_api.php")
    suspend fun getLiveStreams(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_streams"
    ): Response<List<LiveStreamDto>>
    
    @GET("player_api.php")
    suspend fun getLiveCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_categories"
    ): Response<List<CategoryDto>>
    
    @GET("player_api.php")
    suspend fun getVodStreams(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_streams"
    ): Response<List<LiveStreamDto>>
    
    @GET("player_api.php")
    suspend fun getVodCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_categories"
    ): Response<List<CategoryDto>>
    
    @GET("player_api.php")
    suspend fun getSeries(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series"
    ): Response<List<LiveStreamDto>>
    
    @GET("xmltv.php")
    suspend fun getEpgXml(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<String>
}