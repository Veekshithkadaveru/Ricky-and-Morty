package com.example.rickyandmorty.data.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.repo.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character.asStateFlow()

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            try {
                val characterDetail = characterRepository.getCharacterById(characterId)
                _character.value = characterDetail
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}