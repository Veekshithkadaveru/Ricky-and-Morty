package com.example.rickyandmorty.data.repo

import com.example.rickyandmorty.data.datasource.CharacterDataSource
import com.example.rickyandmorty.data.entity.Character

class CharacterRepository(private val cds: CharacterDataSource) {

    suspend fun getAllCharacters(): List<Character> = cds.getAllCharacters()

    suspend fun getFilteredCharacters(name: String?, status: String?): List<Character> =
        cds.getFilteredCharacters(name, status)

    suspend fun getCharacterById(id: Int): Character = cds.getCharacterById(id)

}