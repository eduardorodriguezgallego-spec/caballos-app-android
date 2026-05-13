package com.caballosapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.caballosapp.viewmodel.AuthViewModel

class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("caballosapp", MODE_PRIVATE)
        val tokenGuardado = prefs.getString("token", null)

        if (!tokenGuardado.isNullOrEmpty()) {
            val intent = Intent(this, ReservasActivity::class.java)
            intent.putExtra("token", tokenGuardado)
            startActivity(intent)
            finish()
            return
        }

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
            text = "🐎 Caballos para disfrutar"
            textSize = 26f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(62, 39, 35))
            gravity = Gravity.CENTER
        }

        val subtitulo = TextView(this).apply {
            text = "Reserva tus paseos de hípica"
            textSize = 16f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 28)
        }

        val etEmail = EditText(this).apply {
            hint = "Email"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        val etPassword = EditText(this).apply {
            hint = "Contraseña"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val btnLogin = Button(this).apply {
            text = "Iniciar sesión"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(93, 64, 55))
        }

        val btnRegistro = Button(this).apply {
            text = "Crear cuenta"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(46, 125, 50))
        }

        card.addView(titulo)
        card.addView(subtitulo)
        card.addView(etEmail)
        card.addView(etPassword)
        card.addView(btnLogin)
        card.addView(btnRegistro)

        fondo.addView(card)
        setContentView(fondo)

        btnLogin.setOnClickListener {
            viewModel.login(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }

        btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
        
        viewModel.loginResponse.observe(this) { response ->
            if (response != null) {
                prefs.edit()
                    .putString("token", response.token)
                    .apply()

                val intent = Intent(this, ReservasActivity::class.java)
                intent.putExtra("token", response.token)

                startActivity(intent)
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
