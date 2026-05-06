package com.caballosapp.models

data class Reserva(
    val id: Int,
    val fecha: String,
    val hora: String,
    val comentarios: String?,
    val estado: String,
    val estado_pago: String,
    val caballo: Caballo
)