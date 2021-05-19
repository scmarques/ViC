package com.sephora.calculoidade

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    var datepicker: DatePickerDialog? = null
    var eText: EditText? = null
    var btn: Button? = null
    var tvw: TextView? = null

    var campoDeTexto : EditText? = null
    var campoDeTextoPst : EditText? = null
    lateinit var mensagem: TextView
    lateinit var enviar: Button
    lateinit var listaCadastrados: TextView
    var diasRestantes : Long = 0
    val listaPresentes = mutableListOf<String>()
    var novoCadastro : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eText = findViewById<View>(R.id.editText1) as EditText
        campoDeTexto = findViewById(R.id.edtNome)
        campoDeTextoPst = findViewById(R.id.edtPresente)
        listaCadastrados = findViewById(R.id.txtLista)
        enviar = findViewById(R.id.btnEnviar)

        eText!!.inputType = InputType.TYPE_NULL
        eText!!.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            datepicker = DatePickerDialog(
                this@MainActivity,
                { view, year, monthOfYear, dayOfMonth -> eText!!.setText (dayOfMonth.toString() + "" +
                        "/" + (monthOfYear + 1) + "/" + year)
                    diasRestantes = calcularDias(dayOfMonth, monthOfYear)
                },
                year,
                month,
                day
            )
            datepicker!!.show()
        }


        enviar.setOnClickListener {

            val nomeInserido = campoDeTexto?.text.toString()
            val presenteInserido = campoDeTextoPst?.text.toString()
            val dataEscolhida = eText?.text.toString()
            if (nomeInserido?.isEmpty()){campoDeTexto?.error = "Insira seu nome!"}
            if (presenteInserido?.isEmpty()) {campoDeTextoPst?.error = "Insira o que deseja ganhar!"}
            if (dataEscolhida?.isEmpty()){Toast.makeText(this, "Insira a data utilizando o calendário!", Toast.LENGTH_LONG).show() }
            if (nomeInserido.isNotEmpty() && presenteInserido.isNotEmpty() && dataEscolhida.isNotEmpty()){
                novoCadastro = "Nome: $nomeInserido | Dias até niver: $diasRestantes | Presente: $presenteInserido\n"
                cadastraPresente(novoCadastro)
                Toast.makeText(this, "Olá, $nomeInserido! Cadastro realizado!", Toast.LENGTH_LONG).show()
                campoDeTexto?.setText("")
                campoDeTextoPst?.setText("")
                eText?.setText("")
            }
        }
    }

    public fun calcularDias(dia : Int, mes : Int): Long {
        val cldr = Calendar.getInstance()
        val day = cldr[Calendar.DAY_OF_MONTH]
        val  month = cldr[Calendar.MONTH]+1
        var year = cldr[Calendar.YEAR]
        var ano = year
        if ((month > mes+1) || (month == mes+1 && day > dia)) {ano = year + 1}

        val today = LocalDate.of(year, month, day)
        val birthday = LocalDate.of(ano, mes+1, dia)
        return ChronoUnit.DAYS.between(today, birthday)
    }

    public fun cadastraPresente(cadastro : String){
        var exibir = ""
        listaCadastrados.visibility = View.VISIBLE
        listaPresentes.add(cadastro)
        for (vic in listaPresentes){
            exibir += "*$vic\n"
        }
        listaCadastrados.text = exibir
    }

}