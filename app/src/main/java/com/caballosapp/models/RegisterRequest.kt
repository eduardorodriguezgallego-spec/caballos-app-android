package com.caballosapp.models

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val telefono: String
)
