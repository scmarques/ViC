package com.sephora.tarefa1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    private lateinit var texto : TextView
    private lateinit var btnVoltar : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        texto = findViewById(R.id.txtNome)
        btnVoltar = findViewById(R.id.btnVoltar)

        var nome = "Vitória"
        val qtd = 20
        val frequencia = 0.75 * 90
        criaMensagem(nome, qtd, frequencia, texto)

        val intent = Intent (this, MainActivity::class.java)

        btnVoltar.setOnClickListener(){
            startActivity(intent)
        }

    }

    override fun onStart(){
        super.onStart()
        Log.d("AppMensagem", "Ciclo de vida SECOND - onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.d("AppMensagem", "Ciclo de vida SECOND - onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.d("AppMensagem", "Ciclo de vida SECOND - onPause")
    }


    override fun onStop() {
        super.onStop()
        Log.d("AppMensagem", "Ciclo de vida SECOND - onStop")
        Toast.makeText(this, "Voltando a tela inicial", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d("AppMensagem", "Ciclo de vida SECOND - onDestroy")
    }

    fun criaMensagem(nome : String, qtd : Int, frequencia : Double, texto : TextView){
        texto.text = "Parabéns, " + nome +"!\n\n\nVocê foi uma das " + qtd +
                " selecionadas para o projeto ViC!\n\n\n" +
                "Prepare-se para uma jornada de conhecimento" +
                " e não se esqueça de que você precisará de 75% de frequência, o que totalizará " +
                "o mínimo de $frequencia horas cursadas.\n\n\n#VamosJuntas!"
    }


}