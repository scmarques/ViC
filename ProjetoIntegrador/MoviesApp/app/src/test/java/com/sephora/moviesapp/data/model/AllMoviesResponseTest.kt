package com.sephora.moviesapp.data.model

import org.junit.Assert.*
import org.junit.Test

class AllMoviesResponseTest {

    /*
    * When vote average value is received as Double from API, it will be returned a
    * String with the corresponding percent value  and with the % symbol.
    *
    * Dado que um valor da avaliação média é recebido como tipo Double, será retornando
    * uma String com o valor da avaliação em percentual acrescido do símbolo %.
    */

    @Test
    fun `when a number of type Double is inserted a String with its content will be returned`(){
        val result = getVoteAverageString(7.9)
        assertEquals("79%", result)
    }

    fun getVoteAverageString(voteAverage : Double): String {
        val votePercent = voteAverage * 10
        return "%.0f".format(votePercent) + "%"
    }
}