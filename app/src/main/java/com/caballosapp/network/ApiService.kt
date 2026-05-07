package com.caballosapp.network

import com.caballosapp.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @Headers("Accept: application/json")
    @POST("registro")
    suspend fun registro(@Body body: RegisterRequest): Response<LoginResponse>

    @Headers("Accept: application/json")
    @GET("caballos")
    suspend fun getCaballos(@Header("Authorization") token: String): Response<List<Caballo>>

    @Headers("Accept: application/json")
    @GET("reservas")
    suspend fun getReservas(@Header("Authorization") token: String): Response<List<Reserva>>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json")
    @POST("reservas")
    suspend fun crearReserva(
        @Header("Authorization") token: String,
        @Body body: CrearReservaRequest
    ): Response<ReservaResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json")
    @PUT("reservas/{id}")
    suspend fun actualizarReserva(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body body: CrearReservaRequest
    ): Response<ReservaResponse>

    @Headers("Accept: application/json")
    @DELETE("reservas/{id}")
    suspend fun eliminarReserva(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>

    @Headers("Accept: application/json")
    @POST("pagos")
    suspend fun crearPago(
        @Header("Authorization") token: String,
        @Body body: PagoRequest
    ): Response<Map<String, Any>>
}