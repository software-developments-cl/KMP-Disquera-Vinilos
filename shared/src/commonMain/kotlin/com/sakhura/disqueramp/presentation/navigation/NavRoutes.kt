package com.sakhura.disqueramp.presentation.navigation


/**Rutas de navegacion*/
enum class NavRoutes {
    Home,
    Discos,
    Carrito,
    VerPedidos,
    Perfil
}

/**Lista de las pantallas que aparecerán en la barra de navegación*/
val bottomNavItems = listOf(
    NavRoutes.Home,
    NavRoutes.Discos,
    NavRoutes.Carrito,
    NavRoutes.VerPedidos,
    NavRoutes.Perfil,
)

