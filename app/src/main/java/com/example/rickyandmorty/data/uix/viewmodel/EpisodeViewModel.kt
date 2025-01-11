package com.example.rickyandmorty.data.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.data.entity.Episode
import com.example.rickyandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
    ViewModel() {

    val episodeList = MutableStateFlow<List<Episode>>(emptyList())
    val filteredList = MutableStateFlow<List<Episode>>(emptyList())

    init {
        getEpisodes()
    }

    private fun getEpisodes() {
        viewModelScope.launch(Dispatchers.Main) {
            val episodes = episodeRepository.getAllEpisodes()
            episodeList.value = episodes
            filteredList.value = episodes
        }
    }

    private fun getFilteredEpisodes(query: String) {
        filteredList.value = if (query.isBlank()) {
            episodeList.value
        } else {
            episodeList.value.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}