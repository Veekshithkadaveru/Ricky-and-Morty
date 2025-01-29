package com.example.rickyandmorty.data.db

import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.entity.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterDao {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): CharacterResponse

    @GET("character")
    suspend fun getFilteredCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}

