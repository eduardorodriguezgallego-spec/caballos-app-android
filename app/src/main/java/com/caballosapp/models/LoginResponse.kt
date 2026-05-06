package com.caballosapp.models

data class LoginResponse(
    val mensaje: String,
    val token: String,
    val usuario: Usuario
)