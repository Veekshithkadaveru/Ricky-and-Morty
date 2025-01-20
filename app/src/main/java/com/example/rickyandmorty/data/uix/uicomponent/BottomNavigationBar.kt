package com.example.rickyandmorty.data.uix.uicomponent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController,currentScreen:String){
    NavigationBar(containerColor = Color(0xFF202329)) {
        NavigationBarItem(icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home")},
            label = { Text(text = "Characters")},
            selected = currentScreen=="Home Screen",
            onClick = {navController.navigate("Home Screen")},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color(0xFFB0BEC5),
                unselectedTextColor = Color(0xFFB0BEC5))
            )

        NavigationBarItem(icon = { Icon(imageVector = Icons.Default.Movie, contentDescription = "Episodes")},
            label = { Text(text = "Episodes")},
            selected = currentScreen=="EpisodeScreen",
            onClick = {navController.navigate("EpisodeScreen")},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color(0xFFB0BEC5),
                unselectedTextColor = Color(0xFFB0BEC5))
        )

        NavigationBarItem(icon = { Icon(imageVector = Icons.Default.Place, contentDescription = "Locations")},
            label = { Text(text = "Locations")},
            selected = currentScreen=="LocationScreen",
            onClick = {navController.navigate("LocationScreen")},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color(0xFFB0BEC5),
                unselectedTextColor = Color(0xFFB0BEC5))
        )

        NavigationBarItem(icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")},
            label = { Text(text = "Locations")},
            selected = currentScreen=="SettingScreen",
            onClick = {navController.navigate("SettingScreen")},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color(0xFFB0BEC5),
                unselectedTextColor = Color(0xFFB0BEC5))
        )

    }
}