package com.example.rickyandmorty.data.retrofit

import com.example.rickyandmorty.data.db.CharacterDao
import com.example.rickyandmorty.data.db.EpisodeDao
import com.example.rickyandmorty.data.db.LocationDao

class AppUtils {

    companion object {

        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        fun getCharacterDao(): CharacterDao {
            return RetrofitClient.getClient(BASE_URL).create(CharacterDao::class.java)
        }

        fun getLocationDao(): LocationDao {
            return RetrofitClient.getClient(BASE_URL).create(LocationDao::class.java)
        }

        fun getEpisodeDao(): EpisodeDao {
            return RetrofitClient.getClient(BASE_URL).create(EpisodeDao::class.java)
        }
    }
}