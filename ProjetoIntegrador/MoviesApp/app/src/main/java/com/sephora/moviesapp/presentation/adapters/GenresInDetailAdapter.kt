package com.sephora.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.sephora.moviesapp.data.model.GenreModel
import com.sephora.moviesapp.R

class GenresInDetailAdapter  (var dataSet : MutableList<GenreModel>) :
    RecyclerView.Adapter<GenresInDetailAdapter.GenresInDetailViewHolder> (){

    inner class GenresInDetailViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val genreInDetail = view.findViewById<Chip>(R.id.chBtnGenreBorder)

        override fun onClick(v: View?) {

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresInDetailViewHolder {
        val instanceView = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_genre_border, parent, false)
        return GenresInDetailViewHolder(instanceView)
    }

    override fun onBindViewHolder(holder: GenresInDetailViewHolder, position: Int) {
        holder.genreInDetail.text = dataSet[position].name
    }

    override fun getItemCount(): Int = dataSet.size

    /*  fun updateMovies(MutableListData<StateRs> List<MovieModel>) {
          this.dataSet = movies
          notifyDataSetChanged()
      }
  */
    fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}