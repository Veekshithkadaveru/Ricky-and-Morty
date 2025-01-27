package com.example.rickyandmorty.data.uix.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickyandmorty.data.uix.viewmodel.CharacterDetailViewModel
import com.example.rickyandmorty.data.uix.viewmodel.EpisodeDetailViewModel
import com.example.rickyandmorty.data.uix.viewmodel.EpisodeViewModel
import com.example.rickyandmorty.data.uix.viewmodel.HomeViewModel
import com.example.rickyandmorty.data.uix.viewmodel.LocationDetailViewModel
import com.example.rickyandmorty.data.uix.viewmodel.LocationViewModel
import com.example.rickyandmorty.data.uix.viewmodel.SettingsViewModel

@Composable
fun Transitions(
    homeViewModel: HomeViewModel,
    episodeViewModel: EpisodeViewModel,
    locationViewModel: LocationViewModel,
    characterDetailViewModel: CharacterDetailViewModel,
    episodeDetailViewModel: EpisodeDetailViewModel,
    locationDetailViewModel: LocationDetailViewModel,
    settingViewModel: SettingsViewModel
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "SplashScreen"){

        composable("HomeScreen"){
            HomeScreen(homeViewModel, navController)
        }
        composable("SplashScreen"){
            SplashScreen(navController)
        }
        composable("EpisodeScreen"){
            EpisodeScreen(episodeViewModel, navController)
        }
        composable("LocationScreen"){
            LocationScreen(locationViewModel, navController)
        }
        composable("SettingScreen"){
            SettingsScreen(settingViewModel, navController)
        }
        composable("CharacterDetailScreen/{characterId}",
            arguments = listOf(
                navArgument("characterId"){
                    type = NavType.StringType
                }
            )
        ){
            val characterId = it.arguments?.getString("characterId")
            CharacterDetailScreen(characterDetailViewModel, navController, characterId.toString())
        }
        composable("EpisodeDetailScreen/{episodeId}",
            arguments = listOf(
                navArgument("episodeId"){
                    type = NavType.StringType
                }
            )
        ){
            val episodeId = it.arguments?.getString("episodeId")
            EpisodeDetailScreen(episodeDetailViewModel, navController, episodeId.toString())
        }
        composable("LocationDetailScreen/{locationId}",
            arguments = listOf(
                navArgument("locationId"){
                    type = NavType.StringType
                }
            )
        ){
            val locationId = it.arguments?.getString("locationId")
            DetailScreen(locationDetailViewModel, navController, locationId.toString())
        }

    }
}