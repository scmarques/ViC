package com.sephora.moviesapp.data.model

import org.junit.Assert
import org.junit.Test

class DetailedMovieModelTest {

    /*
    * When runtime of an specific movie is received from API as an INT, it will be returned an
    * String with the runtime in format HH:mm min. If runtime is received as null, it will be returned
    * a string with no content.
    *
    * Dado que um valor da duração do filme é recebido como tipo Int, será retornada
    * uma String com a duração no formato de h:mm min. Caso o valor recebido seja nulo,
    * será retornada uma string vazia.
    */

    @Test
    fun `when runtime is received as an Int it will be return string with hours and minutes`() {
        val result = runtimeMask(129)
        Assert.assertEquals("2h 9min", result)
    }

    @Test
    fun `when runtime is received as a null it will be return string with no content`() {
        val result = runtimeMask(null)
        Assert.assertEquals("", result)
    }

    fun runtimeMask(runtime: Int?): String {
        runtime?.let {
            return ("${runtime / 60}h ${runtime % 60}min")
        }
        return ""
    }


    /*
  * When release date is received from API, it will be returned the first four digits that correspond
  * the year.
  *
  * Dado o recebimento da string contendo as informações da data de lançamento, será retornada uma string
  * contendo apenas os primeiros dígitos, referentes ao ano.
  */
    @Test
    fun `when ReleaseYear is received from API it will be returned an String the last digits that correspond the year`() {
        val result = getReleaseYear("1999-10-12")
        Assert.assertEquals("1999", result)
    }

    fun getReleaseYear(releaseDate: String): String {
        if (releaseDate.isNotEmpty()) return releaseDate.substring(0, 4)
        else return " "
    }


    /*
    * When vote average value is received as Double, it will be returned a String with the corresponding percent value
    * and with the corresponding symbol.
    *
    * Dado que um a valor da avaliação média é inserido no tipo Double, será retornando
    * uma String com o valor da avaliação em percentual.
    */

    @Test
    fun `when a number of type Double is inserted a String with its content its returned`() {
        val result = getVoteAverageString(7.9)
        Assert.assertEquals("79%", result)
    }

    fun getVoteAverageString(voteAverage: Double): String {
        val votePercent = voteAverage * 10
        return "%.0f".format(votePercent) + "%"
    }

}