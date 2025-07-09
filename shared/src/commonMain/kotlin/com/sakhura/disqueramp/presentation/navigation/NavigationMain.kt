package com.sakhura.disqueramp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sakhura.disqueramp.presentation.screens.DiscosScreen
import com.sakhura.disqueramp.presentation.screens.InicioScreen


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
            DiscosScreen()
        }
        composable(route = NavRoutes.Carrito.name) {
//            LibraryScreen()
        }
        composable(route = NavRoutes.VerPedidos.name) {
//            ProfileScreen()
        }
        composable(route = NavRoutes.Perfil.name) {
//            ProfileScreen()
        }
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
                icon = {/*TODO icono por aqui*/ },
                selected = currentDestination?.hierarchy?.any {
                    it.route == topLevelRoute.name
                } == true,
                onClick = {
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

