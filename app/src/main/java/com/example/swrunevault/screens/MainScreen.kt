package com.example.swrunevault.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.screens.home.HomeScreen
import com.example.swrunevault.screens.settings.SettingsScreen
import kotlinx.coroutines.launch

enum class AppScreen{HOME,SETTINGS,RUNES,HELP,SIMULATOR}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    var selectedScreen by remember {
        mutableStateOf(AppScreen.HOME)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(horizontal = 16.dp).verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
                    Text(
                        text = stringResource(R.string.menu_options),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_home))},
                        selected = selectedScreen  == AppScreen.HOME,
                        onClick = {
                            scope.launch {
                                selectedScreen  = AppScreen.HOME
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_runes))},
                        selected = selectedScreen  == AppScreen.RUNES,
                        onClick = {
                            scope.launch {
                                selectedScreen  = AppScreen.RUNES
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_simulator))},
                        selected = selectedScreen  == AppScreen.SIMULATOR,
                        onClick = {
                            scope.launch {
                                selectedScreen  = AppScreen.SIMULATOR
                                drawerState.close()
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_settings))},
                        selected = selectedScreen  == AppScreen.SETTINGS,
                        icon = {Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null)
                        },
                        onClick = {
                            scope.launch {
                                selectedScreen  = AppScreen.SETTINGS
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_help))},
                        selected = selectedScreen  == AppScreen.HELP,
                        icon = {Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Help,
                            contentDescription = null
                        )},
                        onClick = {
                            scope.launch {
                                selectedScreen  = AppScreen.HELP
                                drawerState.close()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.open_menu)
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when(selectedScreen) {
                    AppScreen.HOME -> {
                        HomeScreen()
                    }
                    AppScreen.SETTINGS -> {
                        SettingsScreen()
                    }
                    AppScreen.RUNES -> {
                        RuneScreen()
                    }
                    AppScreen.SIMULATOR -> {
                        SimulatorScreen()
                    }
                    AppScreen.HELP -> {
                        HelpScreen()
                    }
                }
            }
        }
    }
}
