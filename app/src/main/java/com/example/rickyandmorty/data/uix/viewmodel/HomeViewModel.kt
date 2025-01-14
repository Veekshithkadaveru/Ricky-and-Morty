package com.example.rickyandmorty.data.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.entity.Episode
import com.example.rickyandmorty.data.repo.CharacterRepository
import com.example.rickyandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList

    private val _episodeList = MutableStateFlow<List<Episode>>(emptyList())
    val episodeList: StateFlow<List<Episode>> = _episodeList

    init {
        getCharacters()
        getEpisodes()
    }

    private fun getEpisodes() {
        viewModelScope.launch {
            _episodeList.value = episodeRepository.getAllEpisodes()
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _characterList.value = characterRepository.getAllCharacters().shuffled()
        }
    }
}