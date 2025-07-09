package com.sakhura.disqueramp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sakhura.disqueramp.presentation.screens.InicioScreen

/**Pantalla principal que aloja toda el contenido del navbar y cada pantalla*/
@Composable
fun AppMainScreen() {
    val navHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBarMade(navHostController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            AppNavigationGraph(navHostController)
        }
    }
}

/** Grafo de navegacion de la aplicacion donde aparece cada screen.
 * @param navController instancia de NavHostController*/
@Composable
private fun AppNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.name
    ) {
        composable(route = NavRoutes.Home.name) {
            InicioScreen()
        }
        composable(route = NavRoutes.Discos.name) {
//            DiscosScreen()
            DemoScreen("Discos 💿")
        }
        composable(route = NavRoutes.Carrito.name) {
//            Carrito()
            DemoScreen("Carrito 🛒")
        }
        composable(route = NavRoutes.VerPedidos.name) {
//            VerPedidos()
            DemoScreen("Ver Pedidos 🧧")
        }
        composable(route = NavRoutes.Perfil.name) {
//            Perfil()
            DemoScreen("Perfil 🧗")
        }
    }
}

//Luego se borrara
@Composable
private fun DemoScreen(text: String) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text("Estás en $text")
    }
}

/** Barra de navegacion inferior
 * @param navController instancia de NavHostController*/
@Composable
private fun NavigationBarMade(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavItems.forEach { topLevelRoute ->
            NavigationBarItem(
                label = { Text(text = topLevelRoute.name) },
                icon = {
                    when (topLevelRoute) {
                        NavRoutes.Home -> Text("🏠")
                        NavRoutes.Discos -> Text("💿")
                        NavRoutes.Carrito -> Text("🛒")
                        NavRoutes.VerPedidos -> Text("📋")
                        NavRoutes.Perfil -> Text(" 😀 ")
                    }
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == topLevelRoute.name
                } == true,
                onClick = {
                    println("Current destination: ${currentDestination?.route}")
                    println("Navigating to: ${topLevelRoute.name}")
                    navController.navigate(topLevelRoute.name) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

