package com.sakhura.disqueramp.models

import com.sakhura.disqueramp.auth.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val _uiState= MutableStateFlow(AuthUiState())
            val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun toggleAutMode(){
        _uiState.value = _uiState.value.copy(isLoginMode = !_uiState.value.isLoginMode)
    }

    fun loginWithEmail(email: String, password: String){
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessages = null)

        //simular validacion

        if(email.isBlank() || password.isBlank()){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessages = "Por favor ingrese un email y contraseña válidos"
            )
            return
        }
        if(!isValidEmail(email)){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessages = "Por favor ingrese un email válido"
            )
            return
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            isAuthenticated = true
        )

    }

    fun registerWithEmail(email: String, password: String){
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessages = null)

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessages = "Por favor no deje campos en blanco"
            )
            return
        }
        if(!isValidEmail(email)){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessages = "Por favor ingrese un email válido"
            )
            return
        }
        if(password != confirmPassword){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessages = "Las contraseñas no coinciden"
            )
            return
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            isAuthenticated = true
        )
    }

    fun clearError(){
        _uiState.value = _uiState.value.copy(errorMessages = null)
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}