package com.example.rickyandmorty.data.db

import com.example.rickyandmorty.data.entity.Location
import com.example.rickyandmorty.data.entity.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationDao {

    @GET("location")
    suspend fun getAllLocations(@Query("pages") pages: Int): LocationResponse

    @GET("location")
    suspend fun getFilteredLocations(
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): LocationResponse

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): Location

    @GET("location/{ids}")
    suspend fun getLocationsByIds(@Path("ids") ids: String): List<Location>
}