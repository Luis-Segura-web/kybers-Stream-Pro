package com.kybers.streampro.di

import com.kybers.streampro.data.repository.FavoriteRepositoryImpl
import com.kybers.streampro.data.repository.XtreamRepositoryImpl
import com.kybers.streampro.domain.repository.FavoriteRepository
import com.kybers.streampro.domain.repository.XtreamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindXtreamRepository(
        xtreamRepositoryImpl: XtreamRepositoryImpl
    ): XtreamRepository

    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository
}