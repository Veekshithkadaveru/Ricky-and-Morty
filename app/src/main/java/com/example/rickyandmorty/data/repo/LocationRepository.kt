package com.example.rickyandmorty.data.repo

import com.example.rickyandmorty.data.datasource.LocationDataSource
import com.example.rickyandmorty.data.entity.Location

class LocationRepository(private val lds: LocationDataSource) {

    suspend fun getAllLocations(): List<Location> = lds.getAllLocations()

    suspend fun getFilteredLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): List<Location> = lds.getFilteredLocations(name, type, dimension)

    suspend fun getLocationById(id: Int): Location = lds.getLocationById(id)

    suspend fun getLocationByIds(ids: String): List<Location> = lds.getLocationByIds(ids)
}