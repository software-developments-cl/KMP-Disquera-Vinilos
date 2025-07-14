package com.sakhura.disqueramp.repository

package com.sakhura.disqueramp.repository.mocks // O donde quieras poner tus mocks

import com.sakhura.disqueramp.models.Usuario
import com.sakhura.disqueramp.repository.UsuarioRepository
import kotlinx.coroutines.delay // Para simular latencia de red/DB si quieres

class UsuarioRepository : UsuarioRepository {

    private val usuariosEnMemoria = mutableListOf<Usuario>()
    private var proximoId = 1 // Para generar IDs simples para la maqueta

    override suspend fun agregarUsuario(usuario: Usuario): Result<Unit> {
        // delay(500) // Opcional: simular un pequeño retraso
        return try {
            // En una implementación real, el ID podría venir del usuario o ser generado por la DB.
            // Para la maqueta, si el usuario no tiene ID, le asignamos uno.
            val usuarioConId = if (usuario.id.isBlank()) {
                usuario.copy(id = (proximoId++).toString())
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

    override suspend fun editarUsuario(usuario: Usuario): Result<Unit> {
        // delay(500)
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

    override suspend fun consultarUsuario(id: String): Result<Usuario?> {
        // delay(300)
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

    override suspend fun consultarTodosLosUsuarios(): Result<List<Usuario>> {
        // delay(700)
        return try {
            Result.success(ArrayList(usuariosEnMemoria)) // Devolver una copia para evitar modificaciones externas
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun eliminarUsuario(id: String): Result<Unit> {
        // delay(500)
        return try {
            val removido = usuariosEnMemoria.removeIf { it.id == id }
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

    // Método de utilidad para precargar datos (útil para desarrollo/pruebas)
    fun precargarUsuarios(usuarios: List<Usuario>) {
        usuarios.forEach { usr ->
            if (usuariosEnMemoria.none { it.id == usr.id }) {
                usuariosEnMemoria.add(usr)
                // Actualizar proximoId si los IDs son numéricos y mayores
                usr.id.toIntOrNull()?.let { numId ->
                    if (numId >= proximoId) proximoId = numId + 1
                }
            }
        }
    }
}
