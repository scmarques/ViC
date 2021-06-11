package com.sephora.minhaagenda.classes

class Trabalho(nome : String,  nomeOrdena : String, telefone : String,
               val email : String) : Contato (nome, nomeOrdena, telefone){

    override fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n* E-mail: $email\n----------------------------\n"
    }
}
