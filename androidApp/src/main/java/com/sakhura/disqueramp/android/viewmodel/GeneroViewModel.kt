package com.sakhura.disqueramp.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.sakhura.disqueramp.models.Genero
import com.sakhura.disqueramp.repository.GeneroRepository

class GeneroViewModel : ViewModel() {

    private val repository = GeneroRepository()
    val generos = mutableStateListOf<Genero>()

    init {
        generos.addAll(repository.obtenerTodos())
    }

    fun agregar(nombre: String) {
        repository.agregar(nombre)
        generos.clear()
        generos.addAll(repository.obtenerTodos())
    }

    fun eliminar(genero: Genero) {
        repository.eliminar(genero)
        generos.remove(genero)
    }
}
