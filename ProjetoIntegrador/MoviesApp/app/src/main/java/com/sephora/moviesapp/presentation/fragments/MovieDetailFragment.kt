package com.sephora.moviesapp.presentation.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.sephora.moviesapp.GlideApp
import com.sephora.moviesapp.R
import com.sephora.moviesapp.databinding.FragmentMovieDetailBinding
import com.sephora.moviesapp.presentation.viewmodels.MovieDetailViewModel
import com.sephora.moviesapp.presentation.adapters.CastAdapter
import com.sephora.moviesapp.presentation.adapters.GenresInDetailAdapter
import com.sephora.moviesapp.presentation.fragments.SearchResultFragment.Companion.charQuery
import com.sephora.moviesapp.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var castAdapter: CastAdapter
    private lateinit var genresOfMovieAdapter: GenresInDetailAdapter
    private lateinit var viewModel: MovieDetailViewModel
    private val args: MovieDetailFragmentArgs by navArgs()


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailBinding.bind(view)

        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        binding.rvDetailCast.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        castAdapter = CastAdapter(mutableListOf())
        binding.rvDetailCast.adapter = castAdapter

        genresOfMovieAdapter = GenresInDetailAdapter(mutableListOf())
        binding.rvGenresOfMovie.adapter = genresOfMovieAdapter
        binding.rvGenresOfMovie.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )


        viewModel.getMovieDetails(args.movieId, args.local)
        setupObserveDetailedMovie()

        viewModel.getMovieCredits(args.movieId, args.local)
        setupObserveMovieCredits()

        viewModel.getParentalGuidance(args.movieId, args.local)
        setupObserveParentalGuidance()

        setupErrorFoundObserver()
        binding.btnReturn.setOnClickListener() {
            charQuery = ""
            requireActivity().onBackPressed()
        }

    }

    fun setupObserveDetailedMovie() {
        viewModel.movieDetailed.observe(viewLifecycleOwner, { it ->
            it.backdropPath?.let {
                GlideApp.with(this)
                    .load(it.original)
                    .transform(CenterCrop())
                    .into(binding.imgDetailBanner)
                binding.loading.visibility = View.GONE
            }
            if (it.isFavorite == 1) binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            binding.txtMovieTitle.text = it?.title
            binding.txtVoteAverage.text = it?.voteAverage
            binding.txtMovieYear.text = it?.releaseYear
            binding.txtOverview.text = it?.movieOverview
            binding.txtRuntime.text = it?.runtime
            it?.genreResults?.let { genre -> genresOfMovieAdapter.dataset.addAll(genre) }
            genresOfMovieAdapter.notifyDataSetChanged()
            val isFavorite = it?.isFavorite == 1
            binding.btnFavorite.setOnClickListener {

                    movie ->
                viewModel.changeFavoriteStatus(it.movieId, it.isFavorite == 1)
                if (isFavorite) binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                else binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)

            }
        })
    }

    fun setupObserveMovieCredits() {
        viewModel.movieCredits.observe(viewLifecycleOwner, {
            castAdapter.dataset.addAll(it)
            castAdapter.notifyDataSetChanged()
        })
    }

    fun setupObserveParentalGuidance() {
        viewModel.parentalGuidance.observe(viewLifecycleOwner,
            {
                binding.txtParentalGuidance.text = it.parentalGuidance
            })
    }

    fun setupErrorFoundObserver() {
        viewModel.errorFound.observe(viewLifecycleOwner, {
            if (it) {
                val action =
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToSystemFailedFragment()
                findNavController().safeNavigate(action)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

}

