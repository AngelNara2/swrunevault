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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    var selectedItem by remember {
        mutableStateOf(R.string.menu_home)
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
                        selected = selectedItem == R.string.menu_home,
                        onClick = {
                            scope.launch {
                                selectedItem = R.string.menu_home
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_runes))},
                        selected = selectedItem == R.string.menu_runes,
                        onClick = {
                            scope.launch {
                                selectedItem = R.string.menu_runes
                                drawerState.close()
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_settings))},
                        selected = selectedItem == R.string.menu_settings,
                        icon = {Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null)
                               },
                        onClick = {
                            scope.launch {
                                selectedItem = R.string.menu_settings
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = {Text(stringResource(R.string.menu_help))},
                        selected = selectedItem == R.string.menu_help,
                        icon = {Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Help,
                                contentDescription = null
                            )},
                        onClick = {
                            scope.launch {
                                selectedItem = R.string.menu_help
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
                Text(
                    text = "Pantalla: ${stringResource(selectedItem)}" ,
                    modifier = Modifier.padding(16.dp)
                )
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