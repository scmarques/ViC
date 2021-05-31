package com.sephora.simulaelevador


import android.widget.TextView

data class Elevador(private var lotacao : Int,
                    private var andarAtual: Int,
                    private val lotacaoMax : Int,
                    private val andarMax : Int){

    fun alteraAndar (andarAtual: Int) : String {
        var msg : String = ""


        if (andarAtual != 0){
            msg = "$andarAtual º"
        }
        else {
            msg = "TÉRREO"
        }
        return msg
    }

    fun embarca (lotacao: Int, lotacaoMax: Int, textoLotacao : TextView) : Int{
            textoLotacao.text = ""+(lotacao+1)+"/$lotacaoMax"
            return (lotacao+1)
    }

    fun desembarca (lotacao: Int, lotacaoMax: Int, textoLotacao : TextView) : Int{
            textoLotacao.text = ""+(lotacao-1)+"/$lotacaoMax"
            return (lotacao-1)
    }

}