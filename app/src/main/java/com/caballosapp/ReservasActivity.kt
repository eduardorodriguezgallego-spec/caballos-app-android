package com.caballosapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caballosapp.viewmodel.ReservasViewModel
import com.caballosapp.utils.fechaParaApi
import com.caballosapp.utils.formatearFecha

class ReservasActivity : ComponentActivity() {

    private val viewModel: ReservasViewModel by viewModels()

    private lateinit var token: String
    private lateinit var adapter: ReservasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        token = intent.getStringExtra("token") ?: ""

        val recycler = findViewById<RecyclerView>(R.id.recyclerReservas)
        val tvSinReservas = findViewById<TextView>(R.id.tvSinReservas)
        val btnCrearReserva = findViewById<Button>(R.id.btnCrearReserva)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        adapter = ReservasAdapter(
            reservas = emptyList(),

            onEliminar = { reservaId ->
                AlertDialog.Builder(this)
                    .setTitle("Eliminar reserva")
                    .setMessage("¿Seguro que quieres eliminar esta reserva?")
                    .setPositiveButton("Sí") { _, _ ->
                        viewModel.eliminarReserva(token, reservaId)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            },

            onEditar = { reserva ->
                val intent = Intent(this, EditarReservaActivity::class.java)
                intent.putExtra("token", token)
                intent.putExtra("reservaId", reserva.id)
                intent.putExtra("caballoId", reserva.caballo.id)
                intent.putExtra("fecha", reserva.fecha)
                intent.putExtra("hora", reserva.hora)
                intent.putExtra("comentarios", reserva.comentarios ?: "")
                startActivity(intent)
            },

            onPagar = { reserva ->
                val intent = Intent(this, PagarReservaActivity::class.java)
                intent.putExtra("token", token)
                intent.putExtra("reservaId", reserva.id)
                startActivity(intent)
            }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        viewModel.reservas.observe(this) { reservas ->
            if (reservas.isEmpty()) {
                tvSinReservas.text = "No tienes reservas futuras"
                tvSinReservas.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            } else {
                tvSinReservas.visibility = View.GONE
                recycler.visibility = View.VISIBLE
                adapter.actualizar(reservas)
            }
        }

        viewModel.mensaje.observe(this) { mensaje ->
            mensaje?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                viewModel.cargarReservas(token)
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                tvSinReservas.text = it
                tvSinReservas.visibility = View.VISIBLE
            }
        }

        btnCrearReserva.setOnClickListener {
            val intent = Intent(this, CrearReservaActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {

            val prefs = getSharedPreferences("caballosapp", MODE_PRIVATE)
            prefs.edit().clear().apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }

        viewModel.cargarReservas(token)
    }

    override fun onResume() {
        super.onResume()
        if (::token.isInitialized && token.isNotEmpty()) {
            viewModel.cargarReservas(token)
        }
    }
}