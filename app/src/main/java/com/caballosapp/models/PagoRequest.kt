package com.caballosapp.models

data class PagoRequest(
    val reserva_id: Int,
    val plataforma: String,
    val cantidad: Double,
    val comision: Double,
    val referencia_pago: String
)
