package com.example.sesionroom.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sesionroom.R
import com.example.sesionroom.SesionRoom
import com.example.sesionroom.model.local.Deudor
import com.example.sesionroom.model.local.DeudorDAO
import com.example.sesionroom.model.remote.DeudorRemote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create.*
import java.sql.Types.NULL


class CreateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bienvenida()






        bt_guardar.setOnClickListener{
            val nombre = et_nombre.text.toString()
            val telefono = et_telefono.text.toString()
            val cantidad = et_cantidad.text.toString()
            

            if (nombre.isNotEmpty() && telefono.isNotEmpty() && cantidad.isNotEmpty() ) {
                cuardarEnRoom(cantidad, nombre, telefono)
                guardarEnFirebase(nombre,telefono,cantidad)
                cleanEditText()
            }
            else
            {
                Toast.makeText(activity, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()

            }



        }
    }

    private fun cleanEditText() {
        et_nombre.setText("")
        et_telefono.setText("")
        et_cantidad.setText("")
    }

    private fun cuardarEnRoom(cantidad: String, nombre: String, telefono: String) {
        val cant = cantidad.toLong()
        val deudor = Deudor(
            NULL,
            nombre,
            telefono,
            cant
        )
        val deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()
        deudorDAO.crearDeudor(deudor)
    }

    private fun bienvenida() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = mAuth.currentUser
        val correo = user?.email
        Toast.makeText(requireContext(), "Bienvenido $correo", Toast.LENGTH_LONG).show()
    }

    private fun guardarEnFirebase(nombre: String, telefono: String, cantidad: String) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        val id = myRef.push().key
        val deudor :DeudorRemote= DeudorRemote(
        id,
        nombre,
        telefono,
        cantidad.toLong()

        )
        myRef.child(id!!).setValue(deudor)
    }
}