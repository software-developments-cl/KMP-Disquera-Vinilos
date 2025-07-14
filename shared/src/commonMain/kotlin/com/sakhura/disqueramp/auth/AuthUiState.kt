package com.sakhura.disqueramp.auth

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessages: String? = null,
    val isLoginMode: Boolean = true
)