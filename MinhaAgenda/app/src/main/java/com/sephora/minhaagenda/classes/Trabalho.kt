package com.sephora.minhaagenda.classes

class Trabalho(nome : String, telefone : Long, val email : String) : Contato (nome, telefone){

    override fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n* E-mail: $email\n----------------------------\n"
    }
}
