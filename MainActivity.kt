@file:OptIn(ExperimentalMaterial3Api::class)

package dev.kokeneko.nyustproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.kokeneko.nyustproject.ui.theme.BottomNavItem
import dev.kokeneko.nyustproject.ui.theme.NYUSTProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYUSTProjectTheme {

                //make the window unlimited
                //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

                //set the navigation bar color
//                window.navigationBarColor = MaterialTheme.colorScheme.primary.toArgb()
//                window.statusBarColor = MaterialTheme.colorScheme.primary.toArgb()
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                val statusBarColor = MaterialTheme.colorScheme.background
                val navigationBarColor = MaterialTheme.colorScheme.primary
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor,
                        darkIcons = useDarkIcons
                    )

                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = useDarkIcons
                    )
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNav()
                }
            }
        }
    }
}

//Bottom Navigation bar
@Composable
fun BottomNav() {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Create",
            route = "add",
            icon = Icons.Rounded.AddCircle,
        ),
        BottomNavItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Rounded.Settings,
        ),
    )

    val navController = rememberNavController()
    // NavController is passed via parameter
    val backStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
            ){
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                                tint = if (backStackEntry.value?.destination?.route == item.route)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        label = {
                            Text(
                                text = item.name,
                                color = if (backStackEntry.value?.destination?.route == item.route)
                                    MaterialTheme.colorScheme.inverseOnSurface
                                else
                                    MaterialTheme.colorScheme.onPrimary,
                                fontWeight = if (backStackEntry.value?.destination?.route == item.route)
                                    FontWeight.Bold
                                else
                                    FontWeight.Normal,
                            )
                        },
                        selected = backStackEntry.value?.destination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )



                }
            }
        }
    ) {
        NavHost(
            navController,
            startDestination = "home",
            modifier = Modifier.padding(it)){
                composable("home") { Greeting("Home") }
                composable("add") { Greeting2("Create") }
                composable("settings") { Greeting3("Settings") }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Greeting2(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Greeting3(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

