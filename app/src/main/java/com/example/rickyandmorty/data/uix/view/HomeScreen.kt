package com.example.rickyandmorty.data.uix.view

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavController
import com.example.rickyandmorty.data.uix.uicomponent.BottomNavigationBar
import com.example.rickyandmorty.data.uix.viewmodel.HomeViewModel
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

                    }
                }
            }
        }

    }
}