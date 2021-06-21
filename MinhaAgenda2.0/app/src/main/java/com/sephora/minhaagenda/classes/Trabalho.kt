package com.sephora.minhaagenda.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Trabalho(override  val nome : String,  override  var nomeOrdena : String, override val telefone : String,
               val email : String, override val categoria : String) : Contato (nome, nomeOrdena,
    telefone, email, categoria){

    override fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n* E-mail: $email\n----------------------------\n"
    }
}
