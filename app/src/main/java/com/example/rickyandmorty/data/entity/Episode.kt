package com.example.rickyandmorty.data.entity

data class Episode(
    val id: Int,
    val name: String,
    val air_data: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
