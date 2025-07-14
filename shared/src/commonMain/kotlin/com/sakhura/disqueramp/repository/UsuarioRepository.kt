package com.sakhura.disqueramp.repository

import com.sakhura.disqueramp.models.Usuario

class UsuarioRepository {

    private val usuariosEnMemoria = mutableListOf<Usuario>()
    private var proximoId = 1 // Para generar IDs simples para la maqueta

    suspend fun agregarUsuario(usuario: Usuario): Result<Unit> {
        return try {
            // En una implementación real, el ID podría venir del usuario o ser generado por la DB.
            // Para la maqueta, si el usuario no tiene ID, le asignamos uno.
            val usuarioConId = if (usuario.id == null) {
                usuario.copy(id = (proximoId++))
            } else {
                // Si ya tiene un ID (quizás de una fuente externa o prueba), verificar duplicados
                if (usuariosEnMemoria.any { it.id == usuario.id }) {
                    throw IllegalArgumentException("Usuario con ID ${usuario.id} ya existe.")
                }
                usuario
            }
            usuariosEnMemoria.add(usuarioConId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun editarUsuario(usuario: Usuario): Result<Unit> {
        return try {
            val indice = usuariosEnMemoria.indexOfFirst { it.id == usuario.id }
            if (indice != -1) {
                usuariosEnMemoria[indice] = usuario
                Result.success(Unit)
            } else {
                Result.failure(NoSuchElementException("Usuario con ID ${usuario.id} no encontrado para editar."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun consultarUsuario(id: Int): Result<Usuario?> {
        return try {
            val usuario = usuariosEnMemoria.find { it.id == id }
            if (usuario != null) {
                Result.success(usuario)
            } else {
                Result.success(null) // O Result.failure si prefieres manejar "no encontrado" como error
            }
        } catch (e: Exception) { // Aunque con una lista en memoria es menos probable
            Result.failure(e)
        }
    }

    suspend fun consultarTodosLosUsuarios(): Result<List<Usuario>> {
        return try {
            Result.success(ArrayList(usuariosEnMemoria)) // Devolver una copia para evitar modificaciones externas
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun olvidarUsuario(id: Int): Result<Unit> {
        return try {
            //val removido = usuariosEnMemoria.removeIf { it.id == id }
            val removido = usuariosEnMemoria.removeAll { it.id == id }
            if (removido) {
                Result.success(Unit)
            } else {
                Result.failure(NoSuchElementException("Usuario con ID $id no encontrado para eliminar."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Método de utilidad para limpiar la maqueta (útil para pruebas)
    fun limpiarDatos() {
        usuariosEnMemoria.clear()
        proximoId = 1
    }

}
