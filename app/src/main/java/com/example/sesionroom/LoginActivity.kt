package com.example.sesionroom


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sesionroom.model.UsuarioDAO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        BT_ingresar_registro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        BT_ingresar_login.setOnClickListener {
            val correo = ET_correo_login.text.toString()
            val usuarioDAO: UsuarioDAO = SesionRoom.database2.UsuarioDAO()
            val usuario = usuarioDAO.buscarUsuario(correo)
            if (usuario != null) {
                if (ET_contrasena_login.text.toString() == usuario.contrasena) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Contraseña incorrecta ", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, "Por favor, Registrese", Toast.LENGTH_LONG).show()
            }

        }
    }

}