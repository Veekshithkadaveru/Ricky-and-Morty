package com.example.rickyandmorty.data.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmorty.data.entity.Location
import com.example.rickyandmorty.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {

    private    val _locationList=MutableStateFlow<List<Location>>(emptyList())
    val locationList:StateFlow<List<Location>> = _locationList.asStateFlow()

    private val _filteredList=MutableStateFlow<List<Location>>(emptyList())
    val filteredList:StateFlow<List<Location>> = _filteredList.asStateFlow()

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            val locations=locationRepository.getAllLocations()
            _locationList.value=locations
            _filteredList.value=locations
        }
    }

    fun filterLocations(query:String){
        _filteredList.value=if (query.isBlank()){
            _locationList.value
        }else{
           _locationList.value.filter {
                it.name.contains(query,ignoreCase = true)
            }
        }
    }
}