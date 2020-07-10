package com.example.sesionroom


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.sesionroom.model.UsuarioDAO
import com.example.sesionroom.model.Usuario
import kotlinx.android.synthetic.main.activity_register.*
import java.sql.Types
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        BT_enviar_reg.setOnClickListener {
            val nombrereg = ET_nombre_reg.text.toString()
            val correoreg = ET_correo_reg.text.toString()
            val contrasenareg = ET_contrasena_reg.text.toString()
            val repcontrasenareg = ET_repita_contrasena_reg.text.toString()

            //val notificacionesreg = CB_notificaciones_reg.isChecked

            if (nombrereg.isEmpty() || correoreg.isEmpty() || contrasenareg.isEmpty()
                || repcontrasenareg.isEmpty()
            ) {
                Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (contrasenareg.length < 6) {
                    Toast.makeText(this, "La contraseña debe ser de minimo 6 digitos", Toast.LENGTH_SHORT).show()
                }else if (contrasenareg != repcontrasenareg) {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }else if(!isValidEmailId(correoreg)){
                    Toast.makeText(this, "El correo ingresado no es válido", Toast.LENGTH_SHORT).show()
                }
                else {

                    val usuario = Usuario(Types.NULL, nombrereg,correoreg,contrasenareg)
                    val usuarioDAO : UsuarioDAO = SesionRoom.database2.UsuarioDAO()
                    usuarioDAO.crearUsuario(usuario)
                    Toast.makeText(this, "El registro ha sido exitoso", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
        }

    }
    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }


}