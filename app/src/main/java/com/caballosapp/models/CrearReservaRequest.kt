package com.caballosapp.models

data class CrearReservaRequest(
    val caballo_id: Int,
    val fecha: String,
    val hora: String,
    val comentarios: String?
)