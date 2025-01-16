package com.example.rickyandmorty.data.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.entity.Location
import com.example.rickyandmorty.data.repo.CharacterRepository
import com.example.rickyandmorty.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _locationDetail = MutableStateFlow<Location?>(null)
    val locationDetail: StateFlow<Location?> = _locationDetail.asStateFlow()

    private val _residentCharacters = MutableStateFlow<List<Character>>(emptyList())
    val residentCharacters: StateFlow<List<Character>> = _residentCharacters.asStateFlow()

    fun getLocationDetailsWithResidents(locationId: Int) {
        viewModelScope.launch {
            val location = locationRepository.getLocationById(locationId)
            _locationDetail.value = location

            val residents = location.residents.map { url ->
                val characterId = url.substringAfterLast("/").toInt()
                characterRepository.getCharacterById(characterId)
            }
          _residentCharacters.value = residents
        }
    }

}