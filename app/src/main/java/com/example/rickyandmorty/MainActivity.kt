package com.example.rickyandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.rickyandmorty.data.uix.view.AppNavigation
import com.example.rickyandmorty.data.uix.viewmodel.CharacterDetailViewModel
import com.example.rickyandmorty.data.uix.viewmodel.EpisodeDetailViewModel
import com.example.rickyandmorty.data.uix.viewmodel.EpisodeViewModel
import com.example.rickyandmorty.data.uix.viewmodel.HomeViewModel
import com.example.rickyandmorty.data.uix.viewmodel.LocationDetailViewModel
import com.example.rickyandmorty.data.uix.viewmodel.LocationViewModel
import com.example.rickyandmorty.data.uix.viewmodel.SettingsViewModel
import com.example.rickyandmorty.ui.theme.RickyAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val homeViewModel: HomeViewModel by viewModels()
    val episodeViewModel: EpisodeViewModel by viewModels()
    val locationViewModel: LocationViewModel by viewModels()
    val characterDetailViewModel: CharacterDetailViewModel by viewModels()
    val episodeDetailViewModel: EpisodeDetailViewModel by viewModels()
    val locationDetailViewModel: LocationDetailViewModel by viewModels()
    val settingViewModel: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickyAndMortyTheme {
                // A surface container using the 'background' color from the theme
                AppNavigation(
                    homeViewModel,
                    episodeViewModel,
                    locationViewModel,
                    characterDetailViewModel,
                    episodeDetailViewModel,
                    locationDetailViewModel,
                    settingViewModel
                )
            }
        }
    }
}

