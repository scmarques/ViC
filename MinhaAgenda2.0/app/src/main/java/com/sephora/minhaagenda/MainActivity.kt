package com.sephora.minhaagenda

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.sephora.minhaagenda.ExibirContatos.Companion.listaContatos
import com.sephora.minhaagenda.classes.Contato


class MainActivity : AppCompatActivity() {

    private lateinit  var btnPesquisar : Button
    private lateinit var btnCadastrar : Button
    private lateinit var btnExibirTodos : Button
    private lateinit var edtPesquisar : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews ()
    }

    private fun bindViews(){

        btnPesquisar = findViewById(R.id.btnPesquisar)
        btnCadastrar = findViewById(R.id.btnCadastro)
        btnExibirTodos = findViewById(R.id.btnExibir)
        edtPesquisar = findViewById(R.id.edtPesquisar)


        btnPesquisar.setOnClickListener{
            val pesquisa = edtPesquisar?.text.toString().lowercase()
            if (pesquisa.isEmpty()) edtPesquisar.error="Insira o nome a ser pesquisado!"
            else{
                val contatoPesquisa = Contato (pesquisa, "")
                val intent = Intent (this, Pesquisar::class.java)
                intent.putExtra(PESQUISA, contatoPesquisa)
                startActivity(intent)

            }

            edtPesquisar.setText("")
        }

        btnExibirTodos.setOnClickListener{
            if (!listaContatos.isEmpty()){
                val intent = Intent (this, ExibirContatos::class.java)
                intent.putExtra(TIPO, 1)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Agenda vazia!", Toast.LENGTH_LONG).show()
            }


        }

        btnCadastrar.setOnClickListener{
            val intent = Intent (this, CadastrarNovoContato::class.java)
            startActivity(intent)
        }
    }

    companion object{
        val PESQUISA = "Pesquisa"
        val TIPO = "Tipo"
    }




}
