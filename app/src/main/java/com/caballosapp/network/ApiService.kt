package com.caballosapp.network

import com.caballosapp.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    @POST("registro")
    suspend fun registro(
        @Body body: RegisterRequest
    ): Response<LoginResponse>

    @GET("caballos")
    suspend fun getCaballos(
        @Header("Authorization") token: String
    ): Response<List<Caballo>>

    @GET("reservas")
    suspend fun getReservas(
        @Header("Authorization") token: String
    ): Response<List<Reserva>>

    @POST("reservas")
    suspend fun crearReserva(
        @Header("Authorization") token: String,
        @Body body: CrearReservaRequest
    ): Response<ReservaResponse>

    @PUT("reservas/{id}")
    suspend fun actualizarReserva(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body body: CrearReservaRequest
    ): Response<ReservaResponse>

    @DELETE("reservas/{id}")
    suspend fun eliminarReserva(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>

    @POST("pagos")
    suspend fun crearPago(
        @Header("Authorization") token: String,
        @Body body: PagoRequest
    ): Response<Map<String, Any>>
}
