package com.sephora.moviesapp.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sephora.moviesapp.GlideApp
import com.sephora.moviesapp.R
import com.sephora.moviesapp.data.model.DetailedMovieEntity
import com.sephora.moviesapp.databinding.ModelMovieItemBinding

class MoviesAdapter(private val onMovieClick: (movie: DetailedMovieEntity) -> Unit,
                    private val onFavoriteClick : (movieId : Int, isFavorite : Boolean) -> Unit
                    ) :
    PagingDataAdapter<DetailedMovieEntity, MoviesAdapter.MoviesViewHolder>(
        MOVIE_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ModelMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (currentItem != null) {
            var i = currentItem.isFavorite
            holder.bind(currentItem)
        }
    }

    inner class MoviesViewHolder(val binding: ModelMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: DetailedMovieEntity) {

            binding.apply {
                txtInferiorMovieTitle.text = movie.title
                txtVoteAverage.text = movie.voteAverage
                GlideApp.with(itemView)
                    .load(movie.posterPath?.original)
                    .transform(CenterCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgMoviePoster)
                imgMoviePoster.setOnClickListener { onMovieClick.invoke(movie) }
                if (movie.isFavorite == 1) {
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                imgFavorite.setOnClickListener {
                    if (movie.isFavorite == 1) {
                        movie.isFavorite = 0
                        imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    } else {
                        movie.isFavorite = 1
                        imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }
                    run { onFavoriteClick.invoke(movie.movieId, movie.isFavorite == 0) }
                }

            }

        }
    }


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<DetailedMovieEntity>() {
            override fun areItemsTheSame(
                oldItem: DetailedMovieEntity,
                newItem: DetailedMovieEntity
            ) =
                oldItem.movieId == newItem.movieId

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DetailedMovieEntity,
                newItem: DetailedMovieEntity
            ) =
                oldItem == newItem
        }
    }
}
