package com.example.rickyandmorty.data.datasource

import com.example.rickyandmorty.data.db.EpisodeDao
import com.example.rickyandmorty.data.entity.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeDataSource(private val episodeDao: EpisodeDao) {

    suspend fun getAllEpisodes(): List<Episode> = withContext(Dispatchers.IO) {
        val allEpisodes = mutableListOf<Episode>()
        var page = 1
        var hasNextPage: Boolean

        do {
            val response = episodeDao.getAllEpisodes(page)
            val result = response.result ?: emptyList() // Null safety fix
            allEpisodes.addAll(result)
            hasNextPage = response.info.next != null
            page++
        } while (hasNextPage)

        return@withContext allEpisodes
    }

    suspend fun getFilteredEpisodes(name: String? = null, episode: String? = null): List<Episode> =
        withContext(Dispatchers.IO) {
            val response = episodeDao.getFilteredEpisodes(name, episode)
            return@withContext response.result ?: emptyList() // Handle null safely
        }

    suspend fun getEpisodeById(id: Int): Episode = withContext(Dispatchers.IO) {
        return@withContext episodeDao.getEpisodeById(id)
    }

    suspend fun getEpisodeByIds(ids: String): List<Episode> = withContext(Dispatchers.IO) {
        return@withContext episodeDao.getEpisodeByIds(ids) ?: emptyList() // Handle null safely
    }
}
