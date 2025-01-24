package com.example.rickyandmorty.data.uix.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickyandmorty.data.uix.uicomponent.BottomNavigationBar
import com.example.rickyandmorty.data.uix.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(settingViewModel: SettingsViewModel, navController: NavController) {

    var isDarkEnable by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Settings", color = Color(0xFF1F8A70)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
        )
    },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = "SettingScreen"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            TODO("Create DarkMode Toggle, Language Selection, Social Media Section")
        }
    }
}

@Composable
fun DarkModelToggle(isDarkModeEnable: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Dark Mode",
            color = Color(0xFFD1D1D1),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = isDarkModeEnable,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF1F8A70))
        )
    }
}

@Composable
fun LanguageSelection(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Language",
            color = Color(0xFFD1D1D1),
            style = MaterialTheme.typography.bodyLarge
        )

        Box {
            Text(text = selectedLanguage,
                color = Color(0xFF1F8A70),
                modifier = Modifier.clickable { expanded = !expanded })

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                DropdownMenuItem(
                    text = { Text(text = "English") },
                    onClick = {
                        onLanguageSelected("English")
                        expanded = false
                    })

                DropdownMenuItem(
                    text = { Text("Turkish") },
                    onClick = {
                        onLanguageSelected("Turkish")
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("German") },
                    onClick = {
                        onLanguageSelected("German")
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun SocialMediaIcon(iconRes: Int, description: String, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = description,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
    )
}