package com.example.rickyandmorty.data.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.uix.viewmodel.EpisodeDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    episodeDetailViewModel: EpisodeDetailViewModel, navController: NavController, episodeId: String
) {
    val episode by episodeDetailViewModel.episodeDetail.collectAsState()
    val characters by episodeDetailViewModel.characterList.collectAsState()

    LaunchedEffect(episodeId) {
        episodeDetailViewModel.getEpisodeDetailsWithCharacters(episodeId.toInt())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Episode Details", color = Color(0xFF1F8A70)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            episode?.let { episode ->
                Text(
                    text = "Name: ${episode.name}",
                    color = Color(0xFFD1D1D1),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Air Date: ${episode.air_data}",
                    color = Color(0xFFD1D1D1),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Characters",
                    color = Color(0xFF1F8A70),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(characters) {character->
                        CharacterCard(character = character) {
                            navController.navigate("CharacterDetailScreen/{${character.id}}")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, onClick: () -> Unit) {

    val heartColor = when (character.status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
            .background(Color(0xFF202329)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C3E44))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                GlideImage(
                    imageModel = character.image,
                    contentDescription = character.name,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF1F8A70)
                    )
                    Text(
                        text = character.species,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFD1D1D1)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart Icon",
                tint = heartColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

