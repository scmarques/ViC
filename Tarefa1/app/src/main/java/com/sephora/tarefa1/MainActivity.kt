package com.sephora.tarefa1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var btnClique : Button
    lateinit var btnFoto : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClique = findViewById(R.id.btnClique)
        btnFoto = findViewById(R.id.btnFoto)

        val intent = Intent (this, SecondActivity::class.java)

        btnClique.setOnClickListener{
            startActivity(intent)
        }

        btnFoto.setOnClickListener(){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }

    }

    override fun onStart(){
        super.onStart()
        Log.d("AppMensagem", "Ciclo de vida MAIN - onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.d("AppMensagem", "Ciclo de vida MAIN - onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.d("AppMensagem", "Ciclo de vida MAIN - onPause")
    }


    override fun onStop() {
        super.onStop()
        Log.d("AppMensagem", "Ciclo de vida MAIN - onStop")
        Toast.makeText(this, "Saindo da tela inicial", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d("AppMensagem", "Ciclo de vida MAIN - onDestroy")
    }


}
