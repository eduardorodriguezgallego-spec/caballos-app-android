package com.caballosapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.caballosapp.viewmodel.AuthViewModel

class RegistroActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
            setBackgroundColor(Color.WHITE)
        }

        val etNombre = EditText(this).apply {
            hint = "Nombre"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
        }

        val etEmail = EditText(this).apply {
            hint = "Email"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
        }

        val etPassword = EditText(this).apply {
            hint = "Contraseña"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
        }

        val etTelefono = EditText(this).apply {
            hint = "Teléfono"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
        }

        val btnRegistro = Button(this).apply {
            text = "Registrarme"
        }

        layout.addView(etNombre)
        layout.addView(etEmail)
        layout.addView(etPassword)
        layout.addView(etTelefono)
        layout.addView(btnRegistro)

        setContentView(layout)

        btnRegistro.setOnClickListener {
            viewModel.registro(
                nombre = etNombre.text.toString(),
                email = etEmail.text.toString(),
                password = etPassword.text.toString(),
                telefono = etTelefono.text.toString()
            )
        }

        viewModel.loginResponse.observe(this) { response ->
            if (response != null) {
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
