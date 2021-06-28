package com.sandyara.aula18

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FirstFragment : Fragment(){

    lateinit var listaAdapter : ListaAdapter //mudei nome
   //lateinit var ADAPTER : Adapter2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_screen, container, false)
    }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       val rv = view.findViewById<RecyclerView>(R.id.rv_lista_nomes)
       listaAdapter = ListaAdapter(listOf("Nome: Sandy, Idade: 21", "Nome: Joana, Idade: 23"))
       rv.adapter = listaAdapter
       rv.layoutManager = LinearLayoutManager(requireActivity())

   }
}

