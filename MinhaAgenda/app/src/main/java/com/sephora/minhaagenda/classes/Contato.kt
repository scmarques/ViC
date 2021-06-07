package com.sephora.minhaagenda.classes

open class Contato(open val nome : String, open val telefone : Long){

    open fun exibirContato () : String{

        return "* Nome: $nome\n* Telefone: $telefone\n----------------------------\n"
    }

    open fun pesquisaNome (contato : Contato, pesquisa : String) : Boolean{
        if (contato.nome.lowercase().contains(pesquisa)) return true
        return false
    }
}
