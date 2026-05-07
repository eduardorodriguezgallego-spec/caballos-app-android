package com.caballosapp.utils

fun formatearFecha(fechaApi: String?): String {
    if (fechaApi.isNullOrBlank()) return ""

    return try {
        val partes = fechaApi.split("-")

        if (partes.size == 3 && partes[0].length == 4) {
            "${partes[2]}-${partes[1]}-${partes[0]}"
        } else {
            fechaApi
        }
    } catch (e: Exception) {
        fechaApi
    }
}

fun fechaParaApi(fechaPantalla: String?): String {
    if (fechaPantalla.isNullOrBlank()) return ""

    return try {
        val partes = fechaPantalla.split("-")

        if (partes.size == 3 && partes[2].length == 4) {
            "${partes[2]}-${partes[1]}-${partes[0]}"
        } else {
            fechaPantalla
        }
    } catch (e: Exception) {
        fechaPantalla
    }
}