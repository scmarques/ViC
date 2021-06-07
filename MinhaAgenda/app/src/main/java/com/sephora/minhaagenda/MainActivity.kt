package com.sephora.minhaagenda

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.sephora.minhaagenda.classes.Contato
import com.sephora.minhaagenda.classes.Pessoal
import com.sephora.minhaagenda.classes.Trabalho


class MainActivity : AppCompatActivity() {

    private lateinit  var btnPesquisar : ImageButton
    private lateinit var btnSalvar : Button
    private lateinit var btnExibirTodos : Button
    private lateinit var btnLimpar : Button
    private lateinit var edtNome : EditText
    private lateinit var edtTelefone : EditText
    private lateinit var edtRef : EditText
    private lateinit var edtPesquisar : EditText
    private lateinit var rdgTipo : RadioGroup
    private lateinit var txtContatos :TextView
    private lateinit var rbPessoal : RadioButton
    private lateinit var rbTrabalho : RadioButton

    private var contatos : MutableList<Contato> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews ()
    }

    private fun bindViews(){

        edtRef = findViewById(R.id.edtReferencia)
        rdgTipo = findViewById(R.id.rdgTipo)
        btnPesquisar = findViewById(R.id.btnPesquisar)
        btnExibirTodos = findViewById(R.id.btnExibir)
        btnLimpar = findViewById(R.id.btnLimpar)
        btnSalvar = findViewById(R.id.btnSalvar)
        edtPesquisar = findViewById(R.id.edtPesquisar)
        edtNome = findViewById(R.id.edtNome)
        edtTelefone = findViewById(R.id.edtTel)
        //edtTelefone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        txtContatos = findViewById(R.id.txtContatos)
        rbTrabalho = findViewById(R.id.rbTrabalho)
        rbPessoal = findViewById(R.id.rbPessoal)

        btnSalvar.setOnClickListener{
            val nome = edtNome?.text.toString()
            if (nome.isEmpty())  edtNome.error="Insira o nome!"

            val tel = edtTelefone?.text.toString()
            if (tel.isEmpty()) edtTelefone.error="Insira o número do telefone!"

            val refOuEmail = edtRef?.text.toString()
            if (refOuEmail.isEmpty()) edtRef.error = "Insira a informação!"

            if (!((nome.isEmpty() || (tel.isEmpty()) || (refOuEmail.isEmpty())))){
                when (rdgTipo.checkedRadioButtonId){
                    (rbPessoal.id) ->{
                        val novoP = Pessoal (nome, tel.toLong(), refOuEmail)
                        contatos.add(novoP)
                    }

                    (rbTrabalho.id)->{
                        val novoT = Trabalho (nome, tel.toLong(), refOuEmail)
                        contatos.add(novoT)

                    }
                }

            }
            contatos.sortBy { it.nome.lowercase() }
            edtRef.setText("")
            edtTelefone.setText("")
            edtNome.setText("")
        }

        btnPesquisar.setOnClickListener{
            var msg = ""
            val pesquisa = edtPesquisar?.text.toString().lowercase()
            if (pesquisa.isEmpty()) edtPesquisar.error="Insira o nome a ser pesquisado!"
            else{
                for (i in contatos){
                    if (i.pesquisaNome(i, pesquisa)) msg += i.exibirContato()
                }
            }

            if (msg.isEmpty()) txtContatos.setText("Nome não encontrado.")
            else txtContatos.setText(msg)

            edtPesquisar.setText("")
            btnExibirTodos.visibility = View.INVISIBLE
            btnLimpar.visibility = View.VISIBLE
        }

        btnExibirTodos.setOnClickListener{
            if (contatos.size > 0){
                var msg = ""
                for (i in contatos){
                    msg += i.exibirContato()
                }
                txtContatos.setText(msg)

            }
            else  txtContatos.setText("Agenda vazia.")

            btnExibirTodos.visibility = View.INVISIBLE
            btnLimpar.visibility = View.VISIBLE

        }


        btnLimpar.setOnClickListener{
            txtContatos.setText("")
            btnExibirTodos.visibility = View.VISIBLE
            btnLimpar.visibility = View.INVISIBLE
        }


    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {

            val foiChecado = view.isChecked

            when (view.id) {
                R.id.rbPessoal ->
                    if (foiChecado) {
                        edtRef.setHint("Referência")
                        edtRef.setInputType(InputType.TYPE_CLASS_TEXT)
                    }
                R.id.rbTrabalho -> {
                    if (foiChecado) {
                        edtRef.setHint("E-mail")
                        edtRef.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    }
                }
            }

        }
    }

}