package com.sephora.moviesapp.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.sephora.moviesapp.data.model.MovieCreditsEntity
import com.sephora.moviesapp.databinding.ModelCastBinding

class CastAdapter (var dataset : MutableList <MovieCreditsEntity.CastEntity>) :
        RecyclerView.Adapter<CastAdapter.CastViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {

        val binding = ModelCastBinding.inflate( LayoutInflater.from(parent.context),
            parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {

        holder.bind(dataset[position])
    }

        inner class CastViewHolder(private val binding: ModelCastBinding) :
            RecyclerView.ViewHolder(binding.root){


            fun bind(cast: MovieCreditsEntity.CastEntity) {
                binding.apply {
                    txtActorName.text = cast.nameActor
                    txtCharacterName.text = cast.character
                    cast?.profile_path?.url?.let { Log.d("nome", it) }
                    Glide.with(itemView)
                        .load(cast.profile_path?.original)
                        .transform(CenterCrop())
                        .into(imgCastActor)
                }
            }
            }

        override fun getItemCount(): Int = dataset.size

    }
