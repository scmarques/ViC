package com.sephora.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.sephora.moviesapp.R
import com.sephora.moviesapp.data.model.GenreListEntity
import com.sephora.moviesapp.databinding.ModelGenreFilledBinding

class GenreAdapter(
    var dataset: MutableList<GenreListEntity.GenreEntity>,
    private val onGenreClick: (genreId: Int) -> Unit
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
                for (genre in dataset) {
                    val chip = Chip(itemView.context)
                    addChip(chip, genre)
                    chipGroup.addView(chip, chipGroup.childCount - 1)
                    loading.visibility = View.GONE
                }

                chipGroup.setOnCheckedChangeListener { chipGroup, _ ->
                    onGenreClick.invoke(chipGroup.checkedChipId)
                }
            }
        }

    }

    override fun getItemCount(): Int = dataset.size

    fun addChip(chip: Chip, genre: GenreListEntity.GenreEntity){
        chip.text = genre.genreName
        chip.id = genre.genreId
        chip.isCheckable = true
        chip.isCheckedIconVisible = false
        chip.setTextAppearanceResource(R.style.ChipFilledButtonStyle)
        chip.setChipBackgroundColorResource (R.color.btg_chip_state_list)
    }
}
