package com.caballosapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.caballosapp.utils.fechaParaApi
import com.caballosapp.utils.formatearFecha
import com.caballosapp.viewmodel.ReservasViewModel

class EditarReservaActivity : ComponentActivity() {

    private val viewModel: ReservasViewModel by viewModels()
    private lateinit var token: String
    private var reservaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        token = intent.getStringExtra("token") ?: ""
        reservaId = intent.getIntExtra("reservaId", 0)

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
            text = "✏️ Editar reserva"
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(62, 39, 35))
            gravity = Gravity.CENTER
        }

        val subtitulo = TextView(this).apply {
            text = "Actualiza los datos de la reserva"
            textSize = 16f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 28)
        }

        val etCaballoId = EditText(this).apply {
            hint = "ID del caballo"
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(intent.getIntExtra("caballoId", 0).toString())
        }

        val etFecha = EditText(this).apply {
            hint = "Fecha (20-05-2026)"
            setText(formatearFecha(intent.getStringExtra("fecha")))
        }

        val etHora = EditText(this).apply {
            hint = "Hora (10:00)"
            setText(intent.getStringExtra("hora")?.take(5) ?: "")
        }

        val etComentarios = EditText(this).apply {
            hint = "Comentarios"
            inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or
                    InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                    InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
            imeOptions = EditorInfo.IME_ACTION_DONE
            isSingleLine = false
            minLines = 3
            setText(intent.getStringExtra("comentarios") ?: "")
        }

        val btnGuardar = Button(this).apply {
            text = "Actualizar reserva"
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
        card.addView(etCaballoId)
        card.addView(etFecha)
        card.addView(etHora)
        card.addView(etComentarios)
        card.addView(btnGuardar)
        card.addView(btnVolver)

        fondo.addView(card)
        setContentView(fondo)

        btnGuardar.setOnClickListener {
            val caballoId = etCaballoId.text.toString().toIntOrNull()

            if (caballoId == null) {
                Toast.makeText(this, "ID caballo inválido", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val fecha = fechaParaApi(etFecha.text.toString())
            val hora = etHora.text.toString().trim().take(5)

            if (hora !in listOf("10:00", "11:00", "12:00", "13:00")) {
                Toast.makeText(
                    this,
                    "La hora debe ser 10:00, 11:00, 12:00 o 13:00",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            viewModel.actualizarReserva(
                token = token,
                reservaId = reservaId,
                caballoId = caballoId,
                fecha = fecha,
                hora = hora,
                comentarios = etComentarios.text.toString()
            )
        }

        btnVolver.setOnClickListener {
            finish()
        }

        viewModel.mensaje.observe(this) { mensaje ->
            mensaje?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                finish()
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}