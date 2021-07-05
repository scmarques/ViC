package com.sephora.minhaagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import com.sephora.minhaagenda.ExibirContatos.Companion.listaContatos
import com.sephora.minhaagenda.classes.Contato

class Pesquisar : AppCompatActivity() {

    private lateinit var txtPesquisa: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisar)

        bindViews()

    }

    fun bindViews() {

        txtPesquisa = findViewById(R.id.txtPesquisa)

        val pesquisar = intent.extras?.get(MainActivity.PESQUISA) as Contato
        if (pesquisar.nome != "")
        {
            var msg = ""
            for (i in listaContatos) {
                if (i.pesquisaNome(i, pesquisar.nomeOrdena)) msg += i.exibirContato()
            }
            if (msg.isEmpty()) txtPesquisa.setText("Nome não encontrado.")
            else txtPesquisa.setText(msg)
        }

    }

    fun onReturnClick(view: View){

        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}