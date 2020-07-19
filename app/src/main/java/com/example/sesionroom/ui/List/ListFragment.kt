package com.example.sesionroom.ui.List

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sesionroom.R
import com.example.sesionroom.SesionRoom
import com.example.sesionroom.model.local.Deudor
import com.example.sesionroom.model.local.DeudorDAO
import com.example.sesionroom.model.local.DeudoresRVAdapter

class ListFragment : Fragment() {
var allDeudores: List<Deudor> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root : View = inflater.inflate(R.layout.fragment_list, container, false)
        val rv_deudores: RecyclerView = root.findViewById<RecyclerView>(R.id.rv_deudores)

        rv_deudores.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext,
            RecyclerView.VERTICAL,
            false)

        rv_deudores.setHasFixedSize(true)
        var deudorDAO : DeudorDAO = SesionRoom.database.DeudorDAO()
        allDeudores = deudorDAO.getDeudores()
        var deudoresRVAdapter =
            DeudoresRVAdapter(
                requireActivity().applicationContext,
                allDeudores as ArrayList<Deudor>
            )
        rv_deudores.adapter = deudoresRVAdapter
        deudoresRVAdapter.notifyDataSetChanged()
        return root
    }

}