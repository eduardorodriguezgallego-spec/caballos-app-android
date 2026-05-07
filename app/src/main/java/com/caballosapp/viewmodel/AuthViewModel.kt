package com.caballosapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caballosapp.models.LoginRequest
import com.caballosapp.models.LoginResponse
import com.caballosapp.network.ApiClient
import com.caballosapp.network.ApiService
import kotlinx.coroutines.launch
import com.caballosapp.models.RegisterRequest
import com.caballosapp.network.RetrofitClient

class AuthViewModel : ViewModel() {

    val loginResponse = MutableLiveData<LoginResponse?>()
    val error = MutableLiveData<String?>()

    private val api = ApiClient.retrofit.create(ApiService::class.java)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = api.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    loginResponse.value = response.body()
                } else {
                    error.value = "Login incorrecto: ${response.code()}"
                }

            } catch (e: Exception) {
                error.value = "Error conexión: ${e.message}"
            }
        }
    }
    fun registro(nombre: String, email: String, password: String, telefono: String) {
        viewModelScope.launch {
            try {
                val response = api.registro(
                    RegisterRequest(
                        nombre = nombre,
                        email = email,
                        password = password,
                        telefono = telefono
                    )
                )

                if (response.isSuccessful) {
                    loginResponse.value = response.body()
                } else {
                    error.value = "Error en registro: ${response.code()}"
                }

            } catch (e: Exception) {
                error.value = "Error conexión: ${e.message}"
            }
        }
    }
}