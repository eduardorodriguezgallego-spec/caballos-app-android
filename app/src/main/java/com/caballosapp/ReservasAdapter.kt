package com.caballosapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caballosapp.models.Reserva
import com.caballosapp.utils.formatearFecha

class ReservasAdapter(
    private var reservas: List<Reserva>,
    private val onEliminar: (Int) -> Unit,
    private val onEditar: (Reserva) -> Unit,
    private val onPagar: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloReserva)
        val tvDetalle: TextView = view.findViewById(R.id.tvDetalleReserva)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarItem)
        val btnEditar: Button = view.findViewById(R.id.btnEditarItem)
        val btnPagar: Button = view.findViewById(R.id.btnPagarItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserva, parent, false)

        return ReservaViewHolder(view)
    }

    override fun getItemCount(): Int = reservas.size

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservas[position]

        holder.tvTitulo.text = "🐎 Reserva #${reserva.id}"

        val estadoReserva = when (reserva.estado.lowercase().trim()) {
            "confirmada" -> "🟢 Confirmada"
            "pendiente" -> "🟡 Pendiente"
            "cancelada" -> "🔴 Cancelada"
            else -> reserva.estado
        }

        val estadoPago = when (reserva.estado_pago.lowercase().trim()) {
            "pagado" -> "🟢 Pagado"
            "pendiente" -> "🟡 Pendiente"
            "fallido" -> "🔴 Fallido"
            else -> reserva.estado_pago
        }

        holder.tvDetalle.text = """
            📅 Fecha: ${formatearFecha(reserva.fecha)}
            🕒 Hora: ${reserva.hora.take(5)}
            🐴 Caballo: ${reserva.caballo.nombre}

            📌 Estado: $estadoReserva
            💳 Pago: $estadoPago
        """.trimIndent()

        val estadoNormalizado = reserva.estado.lowercase().trim()
        val pagoNormalizado = reserva.estado_pago.lowercase().trim()

        val puedeEditar = estadoNormalizado != "cancelada" && pagoNormalizado != "pagado"
        val puedePagar = estadoNormalizado != "cancelada" && pagoNormalizado != "pagado"

        holder.btnEditar.visibility = if (puedeEditar) View.VISIBLE else View.GONE
        holder.btnEliminar.visibility = View.VISIBLE
        holder.btnPagar.visibility = if (puedePagar) View.VISIBLE else View.GONE

        holder.btnEliminar.setOnClickListener {
            onEliminar(reserva.id)
        }

        holder.btnEditar.setOnClickListener {
            onEditar(reserva)
        }

        holder.btnPagar.setOnClickListener {
            onPagar(reserva)
        }
    }

    fun actualizar(nuevasReservas: List<Reserva>) {
        reservas = nuevasReservas
        notifyDataSetChanged()
    }
}