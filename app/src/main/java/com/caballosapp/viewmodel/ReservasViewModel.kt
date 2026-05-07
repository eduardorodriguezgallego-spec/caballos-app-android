package com.caballosapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caballosapp.models.CrearReservaRequest
import com.caballosapp.models.PagoRequest
import com.caballosapp.models.Reserva
import com.caballosapp.network.RetrofitClient
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException


class ReservasViewModel : ViewModel() {

    val reservas = MutableLiveData<List<Reserva>>()
    val mensaje = MutableLiveData<String?>()
    val error = MutableLiveData<String?>()

    fun cargarReservas(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getReservas("Bearer $token")
                if (response.isSuccessful) {
                    reservas.value = response.body() ?: emptyList()
                } else {
                    error.value = manejarErrorServidor(response)
                }
            } catch (e: Exception) {
                error.value = manejarErrorExcepcion(e)
            }
        }
    }

    fun crearReserva(token: String, caballoId: Int, fecha: String, hora: String, comentarios: String?) {
        viewModelScope.launch {
            try {
                val request = CrearReservaRequest(caballoId, fecha, hora, comentarios ?: "")
                val response = RetrofitClient.api.crearReserva("Bearer $token", request)

                if (response.isSuccessful) {
                    mensaje.value = "¡Perfecto! Tu reserva ha sido guardada."
                    cargarReservas(token)
                } else {
                    error.value = manejarErrorServidor(response)
                }
            } catch (e: Exception) {
                error.value = manejarErrorExcepcion(e)
            }
        }
    }

    fun actualizarReserva(token: String, reservaId: Int, caballoId: Int, fecha: String, hora: String, comentarios: String?) {
        viewModelScope.launch {
            try {
                val request = CrearReservaRequest(caballoId, fecha, hora, comentarios ?: "")
                val response = RetrofitClient.api.actualizarReserva("Bearer $token", reservaId, request)

                if (response.isSuccessful) {
                    mensaje.value = "Reserva actualizada con éxito."
                    cargarReservas(token)
                } else {
                    error.value = manejarErrorServidor(response)
                }
            } catch (e: Exception) {
                error.value = manejarErrorExcepcion(e)
            }
        }
    }

    fun eliminarReserva(token: String, reservaId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.eliminarReserva("Bearer $token", reservaId)
                if (response.isSuccessful) {
                    mensaje.value = "Reserva eliminada con éxito."
                    cargarReservas(token)
                } else {
                    error.value = manejarErrorServidor(response)
                }
            } catch (e: Exception) {
                error.value = manejarErrorExcepcion(e)
            }
        }
    }

    fun pagarReserva(token: String, reservaId: Int, plataforma: String, cantidad: Double, comision: Double, referenciaPago: String) {
        viewModelScope.launch {
            try {
                val request = PagoRequest(reservaId, plataforma, cantidad, comision, referenciaPago)
                val response = RetrofitClient.api.crearPago("Bearer $token", request)

                if (response.isSuccessful) {
                    mensaje.value = "Pago realizado con éxito."
                    cargarReservas(token)
                } else {
                    error.value = manejarErrorServidor(response)
                }
            } catch (e: Exception) {
                error.value = manejarErrorExcepcion(e)
            }
        }
    }

    private fun manejarErrorServidor(response: Response<*>): String {
        val errorBody = response.errorBody()?.string()
        val jsonMsg = extraerMensajeJson(errorBody)
        if (jsonMsg != null) return jsonMsg

        return when (response.code()) {
            401 -> "Tu sesión ha expirado. Por favor, vuelve a entrar."
            409 -> "Ese horario ya no está disponible. Elige otro, por favor."
            422 -> "Hay un error en los datos (fecha o ID). Revisa que todo sea correcto."
            else -> "El servidor tiene problemas. Inténtalo de nuevo más tarde."
        }
    }

    private fun manejarErrorExcepcion(e: Exception): String {
        return if (e is IOException) "No tienes conexión a internet."
        else "Error inesperado al conectar con el servidor."
    }

    private fun extraerMensajeJson(errorBody: String?): String? {
        if (errorBody.isNullOrBlank()) return null
        return try {
            val json = JSONObject(errorBody)
            if (json.has("mensaje")) json.getString("mensaje")
            else if (json.has("message")) json.getString("message")
            else null
        } catch (e: Exception) { null }
    }
}
