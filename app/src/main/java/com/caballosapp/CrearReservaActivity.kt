package com.caballosapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.caballosapp.utils.fechaParaApi
import com.caballosapp.viewmodel.ReservasViewModel
import android.view.inputmethod.EditorInfo

class CrearReservaActivity : ComponentActivity() {

    private val viewModel: ReservasViewModel by viewModels()
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        token = intent.getStringExtra("token") ?: ""

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
            text = "🐎 Crear reserva"
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(62, 39, 35))
            gravity = Gravity.CENTER
        }

        val subtitulo = TextView(this).apply {
            text = "Introduce los datos de la reserva"
            textSize = 16f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 28)
        }

        val etCaballoId = EditText(this).apply {
            hint = "ID del caballo"
            inputType = InputType.TYPE_CLASS_NUMBER
        }

        val etFecha = EditText(this).apply {
            hint = "Fecha (20-05-2026)"
        }

        val etHora = EditText(this).apply {
            hint = "Hora (10:00)"
        }

        val etComentarios = EditText(this).apply {
            hint = "Comentarios"

            inputType =
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or
                        InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                        InputType.TYPE_TEXT_FLAG_AUTO_CORRECT

            imeOptions = EditorInfo.IME_ACTION_DONE

            isSingleLine = false
            minLines = 3
        }

        val btnGuardar = Button(this).apply {
            text = "Guardar reserva"
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

            viewModel.crearReserva(
                token = token,
                caballoId = caballoId,
                fecha = fechaParaApi(etFecha.text.toString()),
                hora = etHora.text.toString().take(5),
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