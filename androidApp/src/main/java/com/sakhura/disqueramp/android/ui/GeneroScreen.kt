package com.sakhura.disqueramp.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sakhura.disqueramp.android.MyApplicationTheme
import com.sakhura.disqueramp.models.Genero

@Composable
fun GeneroScreen(
    generos: List<Genero>,
    onAgregar: (String) -> Unit,
    onEliminar: (Genero) -> Unit
) {
    var nuevoGenero by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nuevoGenero,
            onValueChange = { nuevoGenero = it },
            label = { Text("Nuevo género") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (nuevoGenero.isNotBlank()) {
                    onAgregar(nuevoGenero)
                    nuevoGenero = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(generos) { genero ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(genero.nombre)
                    TextButton(onClick = { onEliminar(genero) }) {
                        Text("Eliminar")
                    }
                }
                Divider()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewGeneroScreen() {
    MyApplicationTheme {
        GeneroScreen(
            generos = listOf(
                Genero(id = 1, nombre = "Rock"),
                Genero(id = 2, nombre = "Jazz")
            ),
            onAgregar = {},
            onEliminar = {}
        )
    }
}