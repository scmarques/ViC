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
    var diasRestantes : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvw = findViewById<View>(R.id.textView1) as TextView
        eText = findViewById<View>(R.id.editText1) as EditText
        campoDeTexto = findViewById(R.id.edtNome)
        campoDeTextoPst = findViewById(R.id.edtPresente)
        mensagem = findViewById(R.id.txtMensagem)
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
            if (dataEscolhida?.isEmpty()){eText?.error = "Insira a data pelo calendário."}
            if (nomeInserido.isNotEmpty() && presenteInserido.isNotEmpty() && dataEscolhida.isNotEmpty()){
                Toast.makeText(this, "Olá, $nomeInserido! Tenho uma mensagem para você!", Toast.LENGTH_LONG).show()

                mensagem?.text = "Olá, $nomeInserido! Faltam $diasRestantes dias para seu aniversário!" +
                            "Espero que você ganhe $presenteInserido!"
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

}