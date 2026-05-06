package com.caballosapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caballosapp.models.CrearReservaRequest
import com.caballosapp.models.PagoRequest
import com.caballosapp.models.Reserva
import com.caballosapp.network.RetrofitClient
import kotlinx.coroutines.launch

class ReservasViewModel : ViewModel() {

    val reservas = MutableLiveData<List<Reserva>>()

    val mensaje = MutableLiveData<String?>()

    val error = MutableLiveData<String?>()

    fun cargarReservas(token: String) {

        viewModelScope.launch {

            try {

                val response = RetrofitClient.api.getReservas(
                    "Bearer $token"
                )

                if (response.isSuccessful) {

                    reservas.value = response.body() ?: emptyList()

                } else {

                    error.value = "Error al cargar reservas: ${response.code()}"
                }

            } catch (e: Exception) {

                error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun crearReserva(
        token: String,
        caballoId: Int,
        fecha: String,
        hora: String,
        comentarios: String?
    ) {

        viewModelScope.launch {

            try {

                val request = CrearReservaRequest(
                    caballo_id = caballoId,
                    fecha = fecha,
                    hora = hora,
                    comentarios = comentarios
                )

                val response = RetrofitClient.api.crearReserva(
                    token = "Bearer $token",
                    body = request
                )

                if (response.isSuccessful) {

                    mensaje.value = "Reserva creada correctamente"

                    cargarReservas(token)

                } else {

                    error.value = "Error al crear reserva: ${response.code()}"
                }

            } catch (e: Exception) {

                error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun actualizarReserva(
        token: String,
        reservaId: Int,
        caballoId: Int,
        fecha: String,
        hora: String,
        comentarios: String
    ) {

        viewModelScope.launch {

            try {

                val request = CrearReservaRequest(
                    caballo_id = caballoId,
                    fecha = fecha,
                    hora = hora,
                    comentarios = comentarios
                )

                val response = RetrofitClient.api.actualizarReserva(
                    token = "Bearer $token",
                    id = reservaId,
                    body = request
                )

                if (response.isSuccessful) {

                    mensaje.value = "Reserva actualizada correctamente"

                    cargarReservas(token)

                } else {

                    error.value = "Error al actualizar reserva: ${response.code()}"
                }

            } catch (e: Exception) {

                error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun pagarReserva(
        token: String,
        reservaId: Int,
        plataforma: String,
        cantidad: Double,
        comision: Double,
        referenciaPago: String
    ) {

        viewModelScope.launch {

            try {

                val response = RetrofitClient.api.crearPago(
                    token = "Bearer $token",
                    body = PagoRequest(
                        reserva_id = reservaId,
                        plataforma = plataforma,
                        cantidad = cantidad,
                        comision = comision,
                        referencia_pago = referenciaPago
                    )
                )

                if (response.isSuccessful) {

                    mensaje.value = "Pago registrado correctamente"

                    cargarReservas(token)

                } else {

                    error.value = "Error al pagar reserva: ${response.code()}"
                }

            } catch (e: Exception) {

                error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun eliminarReserva(
        token: String,
        reservaId: Int
    ) {

        viewModelScope.launch {

            try {

                val response = RetrofitClient.api.eliminarReserva(
                    token = "Bearer $token",
                    id = reservaId
                )

                if (response.isSuccessful) {

                    mensaje.value = "Reserva eliminada correctamente"

                    cargarReservas(token)

                } else {

                    error.value = "Error al eliminar reserva: ${response.code()}"
                }

            } catch (e: Exception) {

                error.value = "Error conexión: ${e.message}"
            }
        }
    }
}