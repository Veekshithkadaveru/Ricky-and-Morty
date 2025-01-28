package com.example.rickyandmorty.data.uix.view

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickyandmorty.data.entity.Character
import com.example.rickyandmorty.data.uix.uicomponent.BottomNavigationBar
import com.example.rickyandmorty.data.uix.uicomponent.ShimmerEffect
import com.example.rickyandmorty.data.uix.viewmodel.HomeViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {

    val characters by viewModel.characterList.collectAsState()

    var currentIndex by remember { mutableIntStateOf(0) }
    var offSetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX = remember { androidx.compose.animation.core.Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ricky and Morty Characters",
                        color = Color(0xFF1F8A70)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = "HomeScreen"
            )
        }
    ) { paddingValues ->
        if (characters.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF121212))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    if (abs(offSetX) > 300) {
                                        val direction = offSetX.sign.toInt()
                                        val targetIndex = (currentIndex - direction).coerceIn(
                                            0,
                                            characters.size - 1
                                        )
                                        if (targetIndex != currentIndex) {
                                            animatedOffsetX.animateTo(
                                                targetValue = offSetX.sign * 1000f,
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = LinearOutSlowInEasing
                                                )

                                            )
                                            currentIndex = targetIndex
                                        }
                                    }
                                    offSetX = 0f
                                    animatedOffsetX.snapTo(0f)
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offSetX += dragAmount.x
                                coroutineScope.launch {
                                    animatedOffsetX.snapTo(offSetX)
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (currentIndex < characters.size) {
                    val character = characters[currentIndex]
                    val nextIndex = (currentIndex + 1).coerceAtMost(characters.size - 1)
                    val nextCharacter = characters[nextIndex]

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CharacterCard(
                            character = nextCharacter,
                            offsetX = -animatedOffsetX.value * 0.2f,
                            alpha = 0.5f
                        ) {}
                        CharacterCard(
                            character = character,
                            offsetX = animatedOffsetX.value,
                            alpha = 1f
                        ) {
                            navController.navigate("CharacterDetailScreen/${character.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, offsetX: Float, alpha: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .graphicsLayer {
                translationX = offsetX
                rotationZ = 0.1f * offsetX / 10
                this.alpha = alpha
                cameraDistance = 16 * density
            }
            .clickable { onClick() }, // Navigates to the character's detail screen when clicked
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C3E44))
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            // Displaying character image
            GlideImage(
                imageModel = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                loading = {
                    ShimmerEffect(modifier = Modifier.fillMaxSize())
                }
            )
            // Displaying character details in a column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF202329), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1F8A70))
                Spacer(modifier = Modifier.height(8.dp))
                CharacterAttributeRow(icon = Icons.Default.Person, label = "", value = character.species)
                CharacterAttributeRow(icon = Icons.Default.Accessibility, label = "", value = character.status)
                CharacterAttributeRow(icon = Icons.Default.Transgender, label = "", value = character.gender)
                CharacterAttributeRow(icon = Icons.Default.Place, label = "", value = character.location.name)
                CharacterAttributeRow(icon = Icons.Default.Bookmark, label = "", value = character.origin.name)
            }
        }
    }
}
@Composable
fun CharacterAttributeRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = "$label Icon", tint = Color(0xFF1F8A70))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFD1D1D1)
        )
    }
}