package com.example.rickyandmorty.data.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.rickyandmorty.data.uix.uicomponent.BottomNavigationBar
import com.example.rickyandmorty.data.uix.viewmodel.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel,
    navController: NavController
) {
    // State for handling the search bar visibility and text input
    val isSearching = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    val locationList by viewModel.filteredList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    if (isSearching.value) {
                        TextField(
                            value = searchText.value,
                            onValueChange = {
                                searchText.value = it
                                viewModel.filterLocations(it) // Filter locations based on search text
                            },
                            label = { Text(text = "Search") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White,
                            )
                        )
                    } else {
                        Text(text = "Rick and Morty Locations", color = Color(0xFF1F8A70))
                    }
                },
                actions = {

                    IconButton(onClick = {
                        isSearching.value = !isSearching.value
                        if (!isSearching.value) {
                            searchText.value = ""
                            viewModel.filterLocations("") // Reset search when exiting search mode
                        }
                    }) {
                        Icon(
                            imageVector = if (isSearching.value) Icons.Filled.Close else Icons.Filled.Search,
                            contentDescription = null,
                            tint = Color(0xFF1F8A70)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, currentScreen = "LocationScreen") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
        ) {
            // LazyColumn to display the list of locations
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(locationList) {

                }
            }
        }
    }
}