package com.sakhura.disqueramp.repository

import com.sakhura.disqueramp.models.Genero

class GeneroRepository {
    private val generos = mutableListOf<Genero>()
    private var nextId = 1L

    fun obtenerTodos(): List<Genero> = generos.toList()

    fun agregar(nombre: String): Genero {
        val nuevo = Genero(id = nextId++, nombre = nombre)
        generos.add(nuevo)
        return nuevo
    }

    fun eliminar(genero: Genero) {
        generos.removeIf { it.id == genero.id }
    }
}