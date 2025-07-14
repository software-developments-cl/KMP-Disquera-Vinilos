package com.sakhura.disqueramp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.sakhura.disqueramp.models.Usuario
import com.sakhura.disqueramp.repository.UsuarioRepository
import kotlinx.coroutines.launch

@Composable
fun EditarUsuario(usuario: Usuario) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var email by remember { mutableStateOf(usuario.email) }
    var telefono by remember { mutableStateOf(usuario.telefono) }
    var pais by remember { mutableStateOf(usuario.pais) }
    var password by remember { mutableStateOf(usuario.password) }
    var confirmarPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    var usuarioRepository = UsuarioRepository()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = telefono.toString(),
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = pais.toString(),
            onValueChange = { pais = it },
            label = { Text("País") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password.toString(),
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmarPassword.toString(),
            onValueChange = { confirmarPassword = it },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = error)
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para guardar los cambios
        Button(onClick = {
            if (password == confirmarPassword) {
                val nuevoUsuario = usuario.copy(
                    nombre = nombre,
                    email = email,
                    telefono = telefono,
                    pais = pais,
                    password = password
                )
                coroutineScope.launch {
                    val result = usuarioRepository.editarUsuario(nuevoUsuario)
                    if (result.isSuccess) {
                        navController.popBackStack()
                    } else {
                        error = "Error al guardar los cambios: ${result.exceptionOrNull()?.message}"
                    }
                }
            } else {
                error = "Las contraseñas no coinciden"
            }
        }) {
            Text("Guardar Cambios")
        }

    }
}

@Composable
fun EditarUsuarioPreview() {
    EditarUsuario(
        usuario = Usuario(
            id = 2,
            nombre = "César",
            email = "",
            password = "",
            telefono = "",
            pais = ""
        )
    )
}