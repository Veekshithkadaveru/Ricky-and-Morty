package com.example.rickyandmorty.data.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.entity.Episode
import com.example.rickyandmorty.data.repo.CharacterRepository
import com.example.rickyandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository
) :
    ViewModel() {

    val episodeDetail = MutableStateFlow<Episode?>(null)
    val characterList=MutableStateFlow<List<Character>>(emptyList())

    fun getEpisodeDetailsWithCharacters(episodeId:Int){
        CoroutineScope(Dispatchers.Main).launch {
            val episode=episodeRepository.getEpisodeById(episodeId)
            episodeDetail.value=episode

            val characters=episode.characters.map { url->
                val characterId=url.substringAfterLast("/").toInt()
                characterRepository.getCharacterById(characterId)
            }
            characterList.value=characters
        }
    }
}