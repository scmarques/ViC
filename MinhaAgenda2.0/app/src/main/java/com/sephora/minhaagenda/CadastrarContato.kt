package com.sephora.minhaagenda

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastrarContato : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    fun onReturnClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
