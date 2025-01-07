package com.example.rickyandmorty.data.datasource

import com.example.rickyandmorty.data.db.CharacterDao
import com.example.rickyandmorty.data.entity.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDataSource(private val characterDao: CharacterDao) {

    suspend fun getAllCharacters(): List<Character> = withContext(Dispatchers.IO) {
        val allCharacters = mutableListOf<Character>()
        var page = 1
        var hasNextPage: Boolean

        do {
            val response = characterDao.getAllCharacters(page)
            allCharacters.addAll(response.results)
            hasNextPage = response.info.next != null
            page++
        } while (hasNextPage)
        return@withContext allCharacters
    }

    suspend fun getFilteredCharacters(name: String?, status: String?): List<Character> =
        withContext(Dispatchers.IO) {
            val response = characterDao.getFilteredCharacters(name, status)
            return@withContext response.results
        }

    suspend fun getCharacterById(id: Int): Character = withContext(Dispatchers.IO) {
        return@withContext characterDao.getCharacterById(id)
    }
}