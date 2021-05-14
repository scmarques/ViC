package com.sephora.tarefa1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var texto : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texto = findViewById(R.id.txtNome)

        var nome = "Vitória"
        val qtd = 20
        val frequencia = 0.75 * 90
        criaMensagem(nome, qtd, frequencia, texto)


    }


    fun criaMensagem(nome : String, qtd: Int, frequencia : Double, texto : TextView) {
        texto.text = "Parabéns, " + nome +"!\n\n\nVocê foi uma das " + qtd +
                " selecionadas para o projeto ViC!\n\n\n" +
                "Prepare-se para uma jornada de conhecimento" +
                " e não se esqueça de que você precisará de 75% de frequência, o que totalizará " +
                "o mínimo de $frequencia horas cursadas.\n\n\n#VamosJuntas!"
    }
}
