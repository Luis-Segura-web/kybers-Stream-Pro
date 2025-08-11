package com.kybers.streampro.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.kybers.streampro.data.local.StreamDatabase
import com.kybers.streampro.data.remote.DynamicUrlInterceptor
import com.kybers.streampro.data.remote.XtreamApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(dynamicUrlInterceptor: DynamicUrlInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(dynamicUrlInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://example.com/") // This will be dynamic based on user input
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideXtreamApi(retrofit: Retrofit): XtreamApi {
        return retrofit.create(XtreamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStreamDatabase(@ApplicationContext context: Context): StreamDatabase {
        return Room.databaseBuilder(
            context,
            StreamDatabase::class.java,
            StreamDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideEpgDao(database: StreamDatabase) = database.epgDao()

    @Provides
    fun provideFavoriteDao(database: StreamDatabase) = database.favoriteDao()
}