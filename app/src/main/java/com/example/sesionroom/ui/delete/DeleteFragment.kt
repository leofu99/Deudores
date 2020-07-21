package com.example.sesionroom.ui.delete

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sesionroom.R
import com.example.sesionroom.SesionRoom
import com.example.sesionroom.model.remote.DeudorRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_delete.*


class DeleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_eliminar.setOnClickListener{
            val nombre = et_nombre.text.toString()
            //eliminarRoom(nombre)
            eliminarFirebase(nombre)
        }
    }


    private fun eliminarFirebase(nombre: String) {

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

                        deudorexiste = true
                        val alertDialog : AlertDialog.Builder? = activity?.let{
                            val builder = AlertDialog.Builder(it)
                            builder.apply {
                                setMessage("Desea eliminar al deudor $nombre?")
                                setPositiveButton("aceptar"){dialog,id->
                                    myRef.child(deudor.id!!).removeValue()

                                }
                                setNegativeButton("cancelar"){dialog,id->

                                }
                            }

                        }
                        alertDialog?.show()


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

    private fun eliminarRoom(nombre: String) {
        val deudorDAO = SesionRoom.database.DeudorDAO()
        val deudor = deudorDAO.buscarDeudor(nombre)

        if (deudor != null) {
            deudorDAO.borrarDeudor(deudor)
            et_nombre.setText("")
        } else
            Toast.makeText(context, "Deudor no encontrado", Toast.LENGTH_SHORT).show()
    }

}