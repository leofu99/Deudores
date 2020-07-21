package com.example.sesionroom.ui.update

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {
 var idFirebase: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_update, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        esconderEt()
        var idDeudor = 0
        val deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()
        bt_buscar.setOnClickListener{
            val nombre = et_nombre.text.toString()



            buscarEnRoom(deudorDAO, nombre, idDeudor)
            buscarEnFirebase(nombre)
        }
        bt_actualizar.setOnClickListener{
           // actualizarEnRoom(idDeudor, deudorDAO)
            actualizarEnFirebase()
            esconderEt()
            bt_buscar.visibility = View.VISIBLE

        }

    }


    private fun actualizarEnFirebase(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        val childUpdate = HashMap<String,Any>()
        childUpdate["nombre"] = et_nombre.text.toString()
        childUpdate["telefono"] = et_telefono.text.toString()
        childUpdate["cantidad"] = et_cantidad.text.toString().toLong()
        myRef.child(idFirebase!!).updateChildren(childUpdate)

    }
    private fun actualizarEnRoom(
        idDeudor: Int,
        deudorDAO: DeudorDAO
    ) {
        val deudor = Deudor(
            idDeudor,
            et_nombre.text.toString(),
            et_telefono.text.toString(),
            et_cantidad.text.toString().toLong()
        )
        deudorDAO.actualizarDeudor(deudor)


    }

    private fun buscarEnFirebase(nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        var deudorexiste = false
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("data",snapshot.toString())
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                    if (deudor?.nombre == nombre){
                        idFirebase = deudor.id
                        deudorexiste = true
                        mostrarEt()
                        et_telefono.setText(deudor.telefono)
                        et_cantidad.setText(deudor.cantidad.toString())
                    }
                }
                if(!deudorexiste){
                    Toast.makeText(context, "Deudor no existe", Toast.LENGTH_SHORT).show()
                    deudorexiste = false

                }

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)

    }

    private fun buscarEnRoom(
        deudorDAO: DeudorDAO,
        nombre: String,
        idDeudor: Int
    ) {
        var idDeudor1 = idDeudor
        val deudor = deudorDAO.buscarDeudor(nombre)
        if (deudor != null) {
            idDeudor1 = deudor.id
            mostrarEt()
            et_telefono.setText(deudor.telefono)
            et_cantidad.setText(deudor.cantidad.toString())


        }
    }

    private fun mostrarEt() {
        et_telefono.visibility = View.VISIBLE
        et_cantidad.visibility = View.VISIBLE
        bt_actualizar.visibility = View.VISIBLE
        bt_buscar.visibility = View.GONE
    }

    private fun esconderEt() {
        et_telefono.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        bt_actualizar.visibility = View.GONE
    }

}