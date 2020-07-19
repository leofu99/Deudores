package com.example.sesionroom


import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    val mAuth :FirebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if(user!= null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        BT_ingresar_registro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        BT_ingresar_login.setOnClickListener {
            val email = ET_correo_login.text.toString()
            val password = ET_contrasena_login.text.toString()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {

                        startActivity(Intent(this, MainActivity::class.java))


                        // Sign in success, update UI with the signed-in user's information

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

}