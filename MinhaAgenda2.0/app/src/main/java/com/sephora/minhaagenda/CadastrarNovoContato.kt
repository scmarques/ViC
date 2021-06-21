package com.sephora.minhaagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.sephora.minhaagenda.classes.Pessoal
import com.sephora.minhaagenda.classes.Trabalho


class CadastrarNovoContato : AppCompatActivity() {

    private lateinit var btnSalvar : Button
    private lateinit var edtNome : EditText
    private lateinit var edtTelefone : EditText
    private lateinit var edtRef : EditText
    private lateinit var rdgTipo : RadioGroup
    private lateinit var rbPessoal : RadioButton
    private lateinit var rbTrabalho : RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_novo_contato)

        bindViews ()
    }

    private fun bindViews(){

        edtRef = findViewById(R.id.edtCampo)
        rdgTipo = findViewById(R.id.rdgTipo)
        btnSalvar = findViewById(R.id.btnSalvar)
        edtNome = findViewById(R.id.edtNome)
        edtTelefone = findViewById(R.id.edtTel)
        edtTelefone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        rbTrabalho = findViewById(R.id.rbTrabalho)
        rbPessoal = findViewById(R.id.rbPessoal)

        edtNome.requestFocus()
        btnSalvar.setOnClickListener{

            val nome = edtNome?.text.toString()
            if (nome.isEmpty())  edtNome.error="Insira o nome!"

            val tel = edtTelefone?.text.toString()
            if (tel.isEmpty()) edtTelefone.error="Insira o número do telefone!"

            val refOuEmail = edtRef?.text.toString()
            if (refOuEmail.isEmpty()) edtRef.error = "Insira a informação!"

            if (!((nome.isEmpty() || (tel.isEmpty()) || (refOuEmail.isEmpty())))){

                val intent = Intent (this, ExibirContatos::class.java)

                when (rdgTipo.checkedRadioButtonId){

                    (rbPessoal.id) ->{
                        val novoP = Pessoal (nome, nome, tel, refOuEmail, "Pessoal")
                        novoP.ordenaContato()
                        intent.putExtra(CONTATO, novoP)
                        startActivity(intent)
                        finishAfterTransition()
                    }

                    (rbTrabalho.id)->{
                        val novoT = Trabalho (nome, nome, tel, refOuEmail, "Trabalho")
                        novoT.ordenaContato()
                        intent.putExtra(CONTATO, novoT)
                        startActivity(intent)
                        finishAfterTransition()

                    }
                }
                edtRef.setText("")
                edtTelefone.setText("")
                edtNome.setText("")
            }
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


    fun onReturnClick(view: View){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finishAfterTransition()
    }

    companion object{
        val CONTATO = "Contato"

    }

}