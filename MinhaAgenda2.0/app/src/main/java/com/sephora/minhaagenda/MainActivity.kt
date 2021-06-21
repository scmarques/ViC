package com.sephora.minhaagenda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.sephora.minhaagenda.ExibirContatos.Companion.listaContatos
import com.sephora.minhaagenda.classes.Contato


class MainActivity : AppCompatActivity() {

    private lateinit var edtPesquisar : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews ()
    }

    private fun bindViews(){

        edtPesquisar = findViewById(R.id.edtPesquisar)
        edtPesquisar.requestFocus()

    }

    companion object{
        val TIPO = "Tipo"
    }

    fun onAddClickMain(view: View) {
        val intent = Intent (this, ExibirContatos::class.java)
        intent.putExtra(TIPO, 3)
        startActivity(intent)
    }

    fun OnShowClick(view: View) {
        if (!listaContatos.isEmpty()){
            val intent = Intent (this, ExibirContatos::class.java)
            intent.putExtra(TIPO, 1)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Agenda vazia!", Toast.LENGTH_LONG).show()
        }
    }

    fun OnSearchClick(view: View) {
        val intent = Intent (this, ExibirContatos::class.java)
        intent.putExtra(TIPO, 2)
        startActivity(intent)
    }
}

