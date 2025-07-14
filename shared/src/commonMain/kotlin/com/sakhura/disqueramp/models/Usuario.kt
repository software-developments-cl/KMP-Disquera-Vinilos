package com.sakhura.disqueramp.models

data class Usuario(
    val id: String,
    val nombre: String,
    val email: String,
    val password: String? = null, // Solo para la entrada del formulario
    val telefono: String?,
    val pais: String?
)
