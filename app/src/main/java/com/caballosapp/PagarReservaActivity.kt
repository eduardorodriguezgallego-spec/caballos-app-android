package com.caballosapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.caballosapp.viewmodel.ReservasViewModel

class PagarReservaActivity : ComponentActivity() {

    private val viewModel: ReservasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra("token") ?: ""

        val reservaId = intent.getIntExtra("reservaId", 0)

        val fondo = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 40, 40, 40)
            setBackgroundColor(Color.rgb(245, 239, 232))
        }

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            setBackgroundColor(Color.WHITE)
        }

        val titulo = TextView(this).apply {
            text = "💳 Pagar reserva"
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(62, 39, 35))
            gravity = Gravity.CENTER
        }

        val subtitulo = TextView(this).apply {
            text = "Introduce los datos del pago"
            textSize = 16f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 28)
        }

        val etCantidad = EditText(this).apply {
            hint = "Cantidad"
        }

        val etComision = EditText(this).apply {
            hint = "Comisión"
        }

        val etReferencia = EditText(this).apply {
            hint = "Referencia STRIPE"
        }

        val btnPagar = Button(this).apply {
            text = "Pagar reserva"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(46, 125, 50))
        }

        val btnVolver = Button(this).apply {
            text = "Volver"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(93, 64, 55))
        }

        card.addView(titulo)
        card.addView(subtitulo)
        card.addView(etCantidad)
        card.addView(etComision)
        card.addView(etReferencia)
        card.addView(btnPagar)
        card.addView(btnVolver)

        fondo.addView(card)

        setContentView(fondo)

        btnPagar.setOnClickListener {

            val cantidad = etCantidad.text.toString().toDoubleOrNull()

            val comision = etComision.text.toString().toDoubleOrNull()

            if (cantidad == null || comision == null) {

                Toast.makeText(
                    this,
                    "Datos inválidos",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            viewModel.pagarReserva(
                token = token,
                reservaId = reservaId,
                plataforma = "Stripe",
                cantidad = cantidad,
                comision = comision,
                referenciaPago = etReferencia.text.toString()
            )
        }

        btnVolver.setOnClickListener {
            finish()
        }

        viewModel.mensaje.observe(this) {

            it?.let { mensaje ->

                Toast.makeText(
                    this,
                    mensaje,
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }

        viewModel.error.observe(this) {

            it?.let { error ->

                Toast.makeText(
                    this,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
