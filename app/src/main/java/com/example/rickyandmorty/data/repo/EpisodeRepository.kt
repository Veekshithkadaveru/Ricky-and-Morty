package com.example.rickyandmorty.data.repo

import com.example.rickyandmorty.data.datasource.EpisodeDataSource
import com.example.rickyandmorty.data.entity.Episode

class EpisodeRepository(private val eds: EpisodeDataSource) {

    suspend fun getAllEpisodes(): List<Episode> = eds.getAllEpisodes()

    suspend fun getFilteredEpisodes(name: String? = null, episode: String? = null): List<Episode> =
        eds.getFilteredEpisodes(name, episode)

    suspend fun getEpisodeById(id: Int): Episode = eds.getEpisodeById(id)

    suspend fun getEpisodesByIds(ids: String): List<Episode> = eds.getEpisodeByIds(ids)

}