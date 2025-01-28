package com.example.rickyandmorty.di

import com.example.rickyandmorty.data.datasource.CharacterDataSource
import com.example.rickyandmorty.data.datasource.EpisodeDataSource
import com.example.rickyandmorty.data.datasource.LocationDataSource
import com.example.rickyandmorty.data.db.CharacterDao
import com.example.rickyandmorty.data.db.EpisodeDao
import com.example.rickyandmorty.data.db.LocationDao
import com.example.rickyandmorty.data.repo.CharacterRepository
import com.example.rickyandmorty.data.repo.EpisodeRepository
import com.example.rickyandmorty.data.repo.LocationRepository
import com.example.rickyandmorty.data.retrofit.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCharacterDao(): CharacterDao {
        return AppUtils.getCharacterDao()
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(): EpisodeDao {
        return AppUtils.getEpisodeDao()
    }

    @Provides
    @Singleton
    fun provideLocationDao(): LocationDao {
        return AppUtils.getLocationDao()
    }

    @Provides
    @Singleton
    fun provideCharacterDataSource(cdao: CharacterDao): CharacterDataSource {
        return CharacterDataSource(cdao)
    }

    @Provides
    @Singleton
    fun provideEpisodeDataSource(edao: EpisodeDao): EpisodeDataSource {
        return EpisodeDataSource(edao)
    }

    @Provides
    @Singleton
    fun provideLocationDataSource(ldao: LocationDao): LocationDataSource {
        return LocationDataSource(ldao)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(cds: CharacterDataSource): CharacterRepository {
        return CharacterRepository(cds)
    }

    @Provides
    @Singleton
    fun provideEpisodeRepository(eds: EpisodeDataSource): EpisodeRepository {
        return EpisodeRepository(eds)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(lds: LocationDataSource): LocationRepository {
        return LocationRepository(lds)
    }

}