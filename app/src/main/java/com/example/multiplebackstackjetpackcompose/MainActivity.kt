package com.example.multiplebackstackjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.multiplebackstackjetpackcompose.ui.theme.MultipleBackstackJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultipleBackstackJetpackComposeTheme {
                val rootNavController = rememberNavController()
                val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEach { item ->
                                val isSelected =
                                    item.title.lowercase() == navBackStackEntry?.destination?.route
                                NavigationBarItem(
                                    selected = isSelected,
                                    label = { Text(text = item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) {
                                                item.selectedIcon
                                            } else {
                                                item.unselectedIcon
                                            }, contentDescription = item.title
                                        )
                                    },
                                    onClick = {
                                        rootNavController.navigate(item.title.lowercase()) {
                                            popUpTo(rootNavController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }

                                )
                            }

                        }
                    }
                ) { padding ->
                    NavHost(rootNavController, startDestination = "home") {
                        composable("home") {
                            HomeNavHost()
                        }
                        composable("chat") {
                            ChatNavHost()
                        }
                        composable("setting") {
                            SettingNavHost()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeNavHost() {
    val homeNavController = rememberNavController()
    NavHost(navController = homeNavController, startDestination = "home1") {
        for (i in 1..10) {
            composable("home$i") {
                GenericScreen(text = "Home $i", onNextClick = {
                    if (i < 10) {
                        homeNavController.navigate("home${i + 1}")
                    }
                })
            }
        }
    }
}

@Composable
fun ChatNavHost() {
    val chatNavController = rememberNavController()
    NavHost(navController = chatNavController, startDestination = "chat1") {
        for (i in 1..10) {
            composable("chat$i") {
                GenericScreen(text = "Chat $i", onNextClick = {
                    if (i < 10) {
                        chatNavController.navigate("chat${i + 1}")
                    }
                })
            }
        }
    }
}

@Composable
fun SettingNavHost() {
    val settingNavController = rememberNavController()
    NavHost(navController = settingNavController, startDestination = "setting1") {
        for (i in 1..10) {
            composable("setting$i") {
                GenericScreen(text = "Setting $i", onNextClick = {
                    if (i < 10) {
                        settingNavController.navigate("setting${i + 1}")
                    }
                })
            }
        }
    }
}

@Composable
fun GenericScreen(
    text: String,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNextClick) {
            Text(text = "Next")

        }

    }


}

data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    ),
    BottomNavigationItem(
        title = "Chat",
        unselectedIcon = Icons.Outlined.Email,
        selectedIcon = Icons.Filled.Email
    ),
    BottomNavigationItem(
        title = "Setings",
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )
)