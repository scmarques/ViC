package com.sephora.fibonacci

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    lateinit var radioGroup : RadioGroup
    lateinit var enviar : Button
    lateinit var saibaMais : Button
    lateinit var campoDeTexto : EditText
    lateinit var resultado : TextView
    lateinit var resultadoT : TextView
    lateinit var saibaTxt : TextView
    var msg : String = "0 | "



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saibaTxt = findViewById(R.id.txtSaiba)
        saibaMais = findViewById(R.id.btnSaibaMais)
        enviar = findViewById(R.id.btnEnviar)
        campoDeTexto = findViewById(R.id.edtNum)
        resultado = findViewById(R.id.txtResultado)
        resultadoT = findViewById(R.id.txtResultadoT)
        radioGroup = findViewById(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext,"Selecionado : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })

        enviar.setOnClickListener(){

            var num = (campoDeTexto?.text.toString())
            if (num.isEmpty()){
                campoDeTexto?.error = "Insira um valor!"

            } else{
                var id: Int = radioGroup.checkedRadioButtonId
                if (id!=-1){
                    val radio:RadioButton = findViewById(id)
                    Toast.makeText(applicationContext,"Computado com : ${radio.text}",
                        Toast.LENGTH_SHORT).show()

                    if (radio.text.toString() == "Recursivo") {
                        for (i in 1 until num.toLong()) {
                            msg += (recursiva(i))
                            msg += " | "
                        }
                    } else {
                        msg = ""
                        var lista = (fibonacciK(num.toInt()).take(num.toInt()).toList())
                        for (i in lista){
                            msg += "$i | "
                        }
                    }
                    resultado.visibility = View.VISIBLE
                    resultadoT.visibility = View.VISIBLE
                    resultado.text = "$msg"
                    resultadoT.text = "Sequência de Fibonacci até a $num ª posição:"
                    saibaTxt.visibility = View.VISIBLE
                    saibaMais.visibility = View.VISIBLE

                }else{
                    Toast.makeText(applicationContext,"Selecione um algoritmo!",
                        Toast.LENGTH_SHORT).show()
                }
                campoDeTexto?.setText("")
                msg = "0 | "

            }
        }
    }

    fun radio_button_click(view: View){
        val radio: RadioButton = findViewById(radioGroup.checkedRadioButtonId)
        Toast.makeText(applicationContext,"Selecionado : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }

    fun recursiva(n: Long): Long = if (n < 2) n else recursiva(n - 1) + recursiva(n - 2)


    fun fibonacciK(num : Int): Sequence<Int> {
            return generateSequence(
                Pair(0, 1),
                { Pair(it.second, it.first + it.second) }).map { it.first }
    }


    fun getUrlFromIntent(view: View) {
        val url = "https://super.abril.com.br/mundo-estranho/o-que-e-a-sequencia-de-fibonacci/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}