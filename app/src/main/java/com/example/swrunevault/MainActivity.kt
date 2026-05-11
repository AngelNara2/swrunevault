package com.example.swrunevault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swrunevault.ui.theme.SWRuneVaultTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SWRuneVaultTheme {
                MainScreen()
            }
        }
    }
}

enum class AppScreen{HOME,SETTINGS,RUNES,HELP}

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

                    AppScreen.HELP -> {
                        HelpScreen()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    SWRuneVaultTheme {
        MainScreen()
    }
}

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Pantalla Inicio",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Bienvenido a SWRuneVault")
    }
}

@Composable
fun RuneScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Runas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Runa Violent",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("⭐ Grado: 6")

                Text("💪 Ataque: +63")

                Text("⚡ Velocidad: +12")

                Text("❤️ HP: +8%")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                // Acción futura
            }
        ) {

            Text("Agregar runa")
        }
    }
}

@Composable
fun SettingsScreen() {

    var darkMode by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Modo oscuro",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                }
            )
        }
    }
}

@Composable
fun HelpScreen() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Ayuda",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Pantalla de ayuda")
    }
}