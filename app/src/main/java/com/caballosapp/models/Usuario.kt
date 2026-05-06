package com.caballosapp.models

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String,
    val rol: String
)