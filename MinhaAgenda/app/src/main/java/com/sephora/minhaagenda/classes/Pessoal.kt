package com.sephora.minhaagenda.classes

class Pessoal(nome: String, telefone: Long, val referencia : String) : Contato (nome, telefone){

    override fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n* Referência: $referencia\n----------------------------\n"
    }
}

