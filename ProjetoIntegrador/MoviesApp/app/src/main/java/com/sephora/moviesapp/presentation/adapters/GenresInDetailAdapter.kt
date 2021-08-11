package com.sephora.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sephora.moviesapp.data.model.GenreModel
import com.sephora.moviesapp.databinding.ModelGenreBorderBinding

class GenresInDetailAdapter  (var dataset : MutableList<GenreModel>) :
    RecyclerView.Adapter<GenresInDetailAdapter.GenresInDetailViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresInDetailViewHolder {
        val binding = ModelGenreBorderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return GenresInDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresInDetailViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class GenresInDetailViewHolder(private val binding: ModelGenreBorderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(genre : GenreModel){
            binding.chBtnGenreBorder.text = genre.name
        }

    }

    override fun getItemCount(): Int = dataset.size

    fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}