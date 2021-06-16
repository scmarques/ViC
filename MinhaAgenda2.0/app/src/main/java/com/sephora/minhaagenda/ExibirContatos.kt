package com.sephora.minhaagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sephora.minhaagenda.classes.Contato

class ExibirContatos : AppCompatActivity() {

    lateinit var txtContatos : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir_contatos)
        bindViews()

    }

    private fun bindViews(){

        txtContatos = findViewById(R.id.txtContatos)

        val tipo = intent.getIntExtra(MainActivity.TIPO, 0)
        if (tipo == 1){
            exibir()
        } else{
            addContato()
            exibir()
        }

    }

    companion object{
        var listaContatos : MutableList <Contato> = mutableListOf ()
    }

    fun exibir () {
        var msg = ""
        for (i in listaContatos) {
            msg += i.exibirContato()
            txtContatos.setText(msg)
        }
    }
    fun addContato (){

        val contato : Contato = intent.extras?.get(CadastrarNovoContato.CONTATO) as Contato
        contato?.let{

            listaContatos.add(contato)
            listaContatos.sortBy { it.nomeOrdena }
        }

    }

    fun onReturnClick(view: View){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}