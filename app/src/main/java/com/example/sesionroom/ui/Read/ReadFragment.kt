package com.example.sesionroom.ui.Read

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sesionroom.R
import com.example.sesionroom.SesionRoom
import com.example.sesionroom.model.local.DeudorDAO
import com.example.sesionroom.model.remote.DeudorRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_read.*


class ReadFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return  inflater.inflate(R.layout.fragment_read, container, false)


    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_buscar.setOnClickListener{
            val nombre =  et_nombre.text.toString()
          //  buscarEnRoom(nombre)
            buscarEnFirebase(nombre)

        }
    }

    private fun buscarEnFirebase(nombre: String) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        var deudorexiste = false
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("data",snapshot.toString())
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                    if (deudor?.nombre == nombre){
                        mostrarDeudor(deudor.nombre,deudor.telefono,deudor.cantidad)
                        deudorexiste = true

                    }
                }
                if(!deudorexiste){
                    Toast.makeText(context, "Deudor no existe", Toast.LENGTH_SHORT).show()
                    deudorexiste = false

                }

            }

        }
        myRef.addValueEventListener(postListener)





    }

    private fun buscarEnRoom(nombre: String) {
        val deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()
        val deudor = deudorDAO.buscarDeudor(nombre)

        if (deudor != null) {
            mostrarDeudor(deudor.nombre,deudor.telefono,deudor.cantidad)
        } else {
            Toast.makeText(context, "Deudor no existe", Toast.LENGTH_SHORT).show()

        }
    }

    private fun mostrarDeudor(nombre: String,telefono:String,cantidad:Long) {
        tv_resultado.text =
            "Nombre: $nombre\n " +
                    "Telefono: $telefono\n " +
                    "Cantidad: $cantidad"
    }
}