package com.example.rickyandmorty.data.datasource

import com.example.rickyandmorty.data.db.LocationDao
import com.example.rickyandmorty.data.entity.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationDataSource(private val locationDao: LocationDao) {

    suspend fun getAllLocations(): List<Location> = withContext(Dispatchers.IO) {

        val allLocations = mutableListOf<Location>()
        var page = 1
        var hasNextPage: Boolean

        do {
            val response = locationDao.getAllLocations(page)
            allLocations.addAll(response.results)
            hasNextPage = response.info.next != null
            page++
        } while (hasNextPage)
        return@withContext allLocations
    }

    suspend fun getFilteredLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): List<Location> =
        withContext(Dispatchers.IO) {

            val response = locationDao.getFilteredLocations(name, type, dimension)
            return@withContext response.results
        }

    suspend fun getLocationById(id: Int): Location = withContext(Dispatchers.IO) {
        return@withContext locationDao.getLocationById(id)
    }

    suspend fun getLocationByIds(ids: String): List<Location> = withContext(Dispatchers.IO) {
        return@withContext locationDao.getLocationsByIds(ids)
    }
}