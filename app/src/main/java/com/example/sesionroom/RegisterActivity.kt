package com.example.sesionroom


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

        BT_enviar_reg.setOnClickListener {

            val password = ET_contrasena_reg.text.toString()
            val email= ET_correo_reg.text.toString()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "Registro melo.",
                            Toast.LENGTH_SHORT
                        ).show()
                       // CrearUsuarioEnBaseDeDatos()
                        onBackPressed()

                    } else {
                        // If sign in fails, display a message to the user.


                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.w("TAG","signInWithEmail:failure",task.getException());

                    }

                    // ...
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