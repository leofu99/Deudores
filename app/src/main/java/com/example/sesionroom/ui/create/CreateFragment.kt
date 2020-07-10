package com.example.sesionroom.ui.create
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.example.sesionroom.MainActivity
import com.example.sesionroom.R
import com.example.sesionroom.SesionRoom
import com.example.sesionroom.model.Deudor
import com.example.sesionroom.model.DeudorDAO
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

        bt_guardar.setOnClickListener{
            val nombre = et_nombre.text.toString()
            val telefono = et_telefono.text.toString()
            val cantidad = et_cantidad.text.toString()
            if (nombre.isNotEmpty() && telefono.isNotEmpty() && cantidad.isNotEmpty() ) {
                val cant = cantidad.toLong()
                val deudor = Deudor(NULL, nombre, telefono, cant)
                val deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()
                deudorDAO.crearDeudor(deudor)
                et_nombre.setText("")
                et_telefono.setText("")
                et_cantidad.setText("")
            }
            else
            {
                Toast.makeText(activity, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()

            }



        }
    }
}