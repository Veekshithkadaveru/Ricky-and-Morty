package com.example.rickyandmorty.data.db

import com.example.rickyandmorty.data.entity.Episode
import com.example.rickyandmorty.data.entity.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeDao {

    @GET("episode")
    suspend fun getAllEpisodes(@Query("pages") pages: Int): EpisodeResponse

    @GET("episode")
    suspend fun getFilteredEpisodes(
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): EpisodeResponse

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Episode

    @GET("episode/{ids}")
    suspend fun getEpisodeByIds(@Path("ids") ids: String): List<Episode>
}