package com.sephora.minhaagenda.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Contato(open val nome : String, open var nomeOrdena : String, open val telefone : String) : Parcelable{

    constructor(nome : String, telefone: String) : this (nome, nome, telefone) {
        nomeOrdena = removeAcento(nome.uppercase())
    }

    open fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n----------------------------\n"
    }


    open fun pesquisaNome (contato : Contato, pesquisa : String) : Boolean{
        if (contato.nomeOrdena.contains(pesquisa)) return true
        return false
    }

    open fun ordenaContato (){
        this.nomeOrdena = removeAcento(nome.uppercase())
    }


    open fun removeAcento(s: String):String{

        if (s == null) {
            return "";
        }

        val chars: CharArray = s.toCharArray()

        var sb = StringBuilder(s)
        var cont = 0

        while (chars.size > cont) {
            var c: Char
            c = chars[cont]
            var c2:String = c.toString()
            c2 = c2.replace("Ã", "A")
            c2 = c2.replace("Õ", "O")
            c2 = c2.replace("Ç", "C")
            c2 = c2.replace("Á", "A")
            c2 = c2.replace("Í", "I")
            c2 = c2.replace("Ó", "O")
            c2 = c2.replace("Ê", "E")
            c2 = c2.replace("É", "E")
            c2 = c2.replace("Ú", "U")

            c = c2.single()
            sb.setCharAt(cont, c)
            cont++
        }
        return sb.toString()

    }
}
