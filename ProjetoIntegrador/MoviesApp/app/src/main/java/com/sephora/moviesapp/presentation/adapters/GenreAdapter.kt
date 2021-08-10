package com.sephora.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sephora.moviesapp.data.model.GenreListEntity
import com.sephora.moviesapp.databinding.ModelGenreFilledBinding

class GenreAdapter(
    var dataset: MutableList<GenreListEntity.GenreEntity>,
    private val onGenreClick: (genre: GenreListEntity.GenreEntity?) -> Unit
) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ModelGenreFilledBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class GenreViewHolder(private val binding: ModelGenreFilledBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(genre: GenreListEntity.GenreEntity) {
            binding.apply {
                chBtnGenreFilled.text = genre.genreName
                chBtnGenreFilled.setOnClickListener() {
                  //  previousGenreId = dataset[layoutPosition].genreId
                    if(chBtnGenreFilled.isChecked){onGenreClick.invoke(dataset[layoutPosition])}
                        else {onGenreClick.invoke(null)}
                }
            }
        }
    }
    override fun getItemCount(): Int = dataset.size

    fun onClick(v: View?) {}

    companion object{
        var previousGenreId: Int = -1
    }
}
