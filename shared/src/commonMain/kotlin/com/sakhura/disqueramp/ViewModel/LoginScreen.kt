package com.sakhura.disqueramp.ViewModel

@Composable
fun LoginScreen(navController: NavController, onGoogleSignIn: () -> Unit) {
    Colunm{
        //agregar
    }
    Button(onclick = { onGoogleSignIn()}){
        Text("Iniciar sesión con Google")

    }
}