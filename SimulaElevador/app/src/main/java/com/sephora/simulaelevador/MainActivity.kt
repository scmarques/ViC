package com.sephora.simulaelevador

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val lotacaoMax = 5
    lateinit private var textoAndar : TextView
    lateinit private var textoAux : TextView
    lateinit private var textoLotacao : TextView
    lateinit private var btnEnviar : Button
    lateinit private var btnEntrar : Button
    lateinit private var btnSair : Button
    lateinit private var recebeAndar : EditText
    lateinit private var imgUp : ImageView
    lateinit private var imgDown : ImageView
    private var andarSelecionado : Int = 0
    private var andarAtual: Int = 0
    private var lotacao : Int = 0
    private var novoAndar : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var handler = Handler ()
        val elevador1 = Elevador(lotacao,0, 5, 8)
        textoAndar = findViewById(R.id.txtAndar)
        textoLotacao = findViewById(R.id.txtLotacao)
        btnEnviar = findViewById(R.id.btnEnviar)
        recebeAndar = findViewById(R.id.edtAndar)
        btnEntrar = findViewById(R.id.btnEntrar)
        btnSair = findViewById(R.id.btnSair)
        imgUp = findViewById(R.id.imgUp)
        imgDown = findViewById(R.id.imgDown)

        btnEnviar.setOnClickListener(){
            var msg = recebeAndar?.text.toString()
            if (msg.isEmpty()){
                recebeAndar.error="Insira o andar!"
            } else {
                andarSelecionado = msg.toInt()
                if (andarSelecionado < 8 && andarSelecionado >= 0) {
                    if (andarSelecionado > andarAtual) {

                        novoAndar = andarAtual
                        val runnable: Runnable = object : Runnable {
                            var i = 0
                            override fun run() {
                                i = 1
                                while (i <= (andarSelecionado - novoAndar)) {
                                    handler.postDelayed({
                                        textoAndar.setText(elevador1.alteraAndar(i + novoAndar))
                                        imgUp.visibility = View.VISIBLE

                                    }, 0)

                                    SystemClock.sleep(1000)
                                    i++
                                }
                            }
                        }
                        Thread(runnable).start()
                        andarAtual = andarSelecionado

                    } else {
                        novoAndar = andarAtual


                        val runnable: Runnable = object : Runnable {
                            var i = 0
                            override fun run() {
                                i = 1
                                while (i <= (novoAndar - andarSelecionado)) {
                                    handler.postDelayed({
                                        textoAndar.setText(elevador1.alteraAndar(novoAndar - i))
                                        imgDown.visibility = View.VISIBLE

                                    }, 0)

                                    SystemClock.sleep(1000)
                                    i++
                                }
                            }
                        }
                        Thread(runnable).start()
                        andarAtual = andarSelecionado

                    }
                }
                else {
                    recebeAndar.error="Andar inexistente!"
                }
                recebeAndar.setText("")
            }
            imgDown.visibility = View.INVISIBLE
            imgUp.visibility = View.INVISIBLE
        }
        btnEntrar.setOnClickListener(){
            if (lotacao < 5) lotacao = elevador1.embarca(lotacao, lotacaoMax, textoLotacao)
            else Toast.makeText(this,"Lotação máxima! Vá de escadas!", Toast.LENGTH_LONG).show()
        }

        btnSair.setOnClickListener(){
            if (lotacao > 0) lotacao = elevador1.desembarca(lotacao, lotacaoMax, textoLotacao)
            else Toast.makeText(this,"Elevador vazio.", Toast.LENGTH_LONG).show()
        }

    }
}