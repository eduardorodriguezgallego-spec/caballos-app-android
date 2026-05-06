package com.caballosapp.models

data class Caballo(
    val id: Int,
    val nombre: String,
    val raza: String?,
    val fecha_nacimiento: String?,
    val foto: String?,
    val foto_url: String?,
    val enfermo: Int?,
    val observaciones: String?
)