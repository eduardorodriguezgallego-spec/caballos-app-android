package com.caballosapp.utils

fun formatearFecha(fecha: String?): String {
    if (fecha.isNullOrBlank()) return ""

    return try {
        val partes = fecha.split("-")
        if (partes.size == 3) {
            "${partes[2]}/${partes[1]}/${partes[0]}"
        } else {
            fecha
        }
    } catch (e: Exception) {
        fecha
    }
}