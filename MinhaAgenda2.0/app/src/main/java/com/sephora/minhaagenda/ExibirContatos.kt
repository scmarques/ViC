package com.sephora.minhaagenda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sephora.minhaagenda.classes.Contato
import kotlinx.android.synthetic.main.activity_exibir_contatos.*


class ExibirContatos : AppCompatActivity(), ContatosAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    private lateinit var rvContatos : RecyclerView
    private lateinit var contactAdapter : ContatosAdapter
    private lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir_contatos)

        bindViews()

    }


    private fun bindViews(){
        searchView = findViewById(R.id.searchView)
        rvContatos = findViewById(R.id.rcContact)

        searchView.setOnQueryTextListener(this)
        setRecyclerView(listaContatos)

        val tipo = intent.getIntExtra(MainActivity.TIPO, 0)
        if (tipo == 1){
            setRecyclerView(listaContatos)
        } else if (tipo == 2){
            searchView.requestFocus()
            searchView.setIconified(false)
        } else if (tipo == 3){
            val intent = Intent (this, CadastrarNovoContato::class.java)
            startActivity(intent)
            finishAfterTransition()
        }else{
            setRecyclerView(listaContatos)
            addContato()

        }


    }

    companion object{
        var listaContatos : MutableList <Contato> = mutableListOf ()
    }



    override fun onBtnDeleteClick(position: Int) {
            val nome = listaContatos[position].nome
            listaContatos.removeAt(position)
            contactAdapter.notifyItemRemoved(position)
            Toast.makeText(
                this, "O contato $nome foi excluído!",
                Toast.LENGTH_SHORT
            ).show()
    }


    fun addContato (){

        val contato : Contato = intent.extras?.get(CadastrarNovoContato.CONTATO) as Contato
        contato?.let{

            listaContatos.add(contato)
            listaContatos.sortBy { it.nomeOrdena }
            contactAdapter.notifyDataSetChanged()
        }

    }

    fun onReturnClick(view: View){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finishAfterTransition()
    }


    private fun searchByName (query : String){

        val filteredList = mutableListOf<Contato>()
        for (contato in listaContatos){
            if (contato.nomeOrdena.startsWith(query.uppercase())) {
                filteredList.add(contato)
            }
        }

        setRecyclerView(filteredList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchByName(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchByName(query)
        }
        return true
    }

    private fun setRecyclerView(lista: MutableList<Contato>) {
        contactAdapter = ContatosAdapter(dataSet = lista, this)
        rvContatos.adapter = contactAdapter
        rvContatos.layoutManager = LinearLayoutManager(this)
    }

    fun onAddClick(view: View) {
        val intent = Intent (this, CadastrarNovoContato::class.java)
        startActivity(intent)
        finishAfterTransition()
    }
}