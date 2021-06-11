package com.sephora.minhaagenda.classes

class Pessoal(nome: String, nomeOrdena : String, telefone: String,
              val referencia : String) : Contato (nome, nomeOrdena, telefone){

    override fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n* Referência: $referencia\n----------------------------\n"
    }
}

