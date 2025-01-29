package com.example.rickyandmorty.data.uix.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickyandmorty.data.entity.Episode
import com.example.rickyandmorty.data.uix.uicomponent.BottomNavigationBar
import com.example.rickyandmorty.data.uix.viewmodel.EpisodeViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeScreen(viewModel: EpisodeViewModel, navController: NavController) {

    val isSearching = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    val episodeList by viewModel.filteredList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching.value) {
                        TextField(
                            value = searchText.value, onValueChange = {
                                searchText.value = it
                                viewModel.filteredList
                            },
                            label = { Text(text = "Search") },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White,
                            )
                        )
                    } else {
                        Text(text = "Ricky and Morty Episodes", color = Color(0xFF1F8A70))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isSearching.value = !isSearching.value
                        if (!isSearching.value) {
                            searchText.value = ""
                            viewModel.filteredList
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
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = "EpisodeScreen"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(episodeList) { episode ->
                    EpisodeCard(episode = episode, navController = navController)

                }
            }
        }
    }
}

@Composable
fun EpisodeCard(episode: Episode, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val animatedOffsetX = remember { Animatable(0f) }
    val maxDragOffset = 0.25f * 1080f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer { translationX = animatedOffsetX.value }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (abs(animatedOffsetX.value) > maxDragOffset * 0.75f) {
                                navController.navigate("EpisodeDetailScreen/${episode.id}")
                            }
                            animatedOffsetX.animateTo(0f, animationSpec = tween(500))
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            val newOffset = animatedOffsetX.value + dragAmount
                            if (newOffset >= -maxDragOffset && newOffset <= 0f) {
                                animatedOffsetX.snapTo(newOffset)
                            }
                        }
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C3E44))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${episode.id}.${episode.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1F8A70)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Air Date: ${episode.air_data}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD1D1D1)
                )
                Text(
                    text = "Episode: ${episode.episode}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD1D1D1)
                )
            }
            if (animatedOffsetX.value < 0 && abs(animatedOffsetX.value) > maxDragOffset * 0.5f) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFF1F8A70),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(32.dp)
                )
            }
        }
    }
}
