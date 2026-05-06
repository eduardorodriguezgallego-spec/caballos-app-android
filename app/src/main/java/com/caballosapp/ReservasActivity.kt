package com.caballosapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caballosapp.viewmodel.ReservasViewModel
import androidx.appcompat.app.AlertDialog

class ReservasActivity : ComponentActivity() {

    private val viewModel: ReservasViewModel by viewModels()

    private lateinit var token: String
    private lateinit var adapter: ReservasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reservas)

        token = intent.getStringExtra("token") ?: ""

        val recycler = findViewById<RecyclerView>(R.id.recyclerReservas)

        val btnCrearReserva = findViewById<Button>(R.id.btnCrearReserva)

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        adapter = ReservasAdapter(

            reservas = emptyList(),

            onEliminar = { reservaId ->

                androidx.appcompat.app.AlertDialog.Builder(this)
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

            adapter.actualizar(reservas)
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
            }
        }

        btnCrearReserva.setOnClickListener {

            val intent = Intent(this, CrearReservaActivity::class.java)

            intent.putExtra("token", token)

            startActivity(intent)
        }

        btnLogout.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))

            finish()
        }

        viewModel.cargarReservas(token)
    }

    override fun onResume() {

        super.onResume()

        viewModel.cargarReservas(token)
    }
}