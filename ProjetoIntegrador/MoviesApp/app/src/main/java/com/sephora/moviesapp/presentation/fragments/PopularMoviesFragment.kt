package com.sephora.moviesapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sephora.moviesapp.*
import com.sephora.moviesapp.data.model.DetailedMovieEntity
import com.sephora.moviesapp.databinding.FragmentPopularMoviesBinding
import com.sephora.moviesapp.presentation.adapters.GenreAdapter
import com.sephora.moviesapp.presentation.adapters.MoviesAdapter
import com.sephora.moviesapp.presentation.adapters.MoviesLoadStateAdapter
import com.sephora.moviesapp.presentation.fragments.SearchResultFragment.Companion.charQuery
import com.sephora.moviesapp.presentation.viewmodels.CollectionFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies) {

    private val viewModel by viewModels<CollectionFragmentViewModel>()
    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreListAdapter: GenreAdapter
    private lateinit var movieAdapter: MoviesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = FragmentPopularMoviesBinding.bind(view)

        movieAdapter = MoviesAdapter({ movie -> showMovieDetails(movie) },
            { movieId, isFavorite ->
                changeFavoriteStatus(movieId = movieId, isFavorite = isFavorite)
            })

        binding.apply {
            rvItemModel.setHasFixedSize(true)

            rvItemModel.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter(),
                footer = MoviesLoadStateAdapter())
        }

        binding.rvItemModel.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvBtnGenre.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        genreListAdapter = GenreAdapter(mutableListOf()) { genre -> showByGenre(genre) }
        binding.rvBtnGenre.adapter = genreListAdapter

        viewModel.getGenreList(remote = true)
        setupObserveGenresList()

        viewModel.filterByGenre("")
        setupObserveMoviesList()

        setupErrorFoundObserver()
    }

    private fun changeFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        viewModel.changeFavoriteStatus(movieId, isFavorite)
    }

    private fun showMovieDetails(movie: DetailedMovieEntity) {
        val action = CollectionFragmentDirections
            .actionCollectionFragmentToMovieDetailFragment(
                movieId = movie.movieId,
                local = "remote"
            )
        findNavController().safeNavigate(action)
    }

    private fun showByGenre(genreId: Int) {
        if (genreId != -1) viewModel.filterByGenre(genreId.toString())
        else viewModel.filterByGenre("")
        setupObserveMoviesList()
    }

    fun setupObserveMoviesList() {
        viewModel.moviesList.observe(viewLifecycleOwner, {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            movieAdapter.notifyDataSetChanged()
            binding.loading.visibility = View.GONE
        })
    }

    fun setupObserveGenresList() {
        viewModel.genreList.observe(viewLifecycleOwner, {
            genreListAdapter.dataset.addAll(it)
            genreListAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        viewModel.filterByGenre("")
        val isConnected = viewModel.checkNetWorkStatus(requireContext())
        if(isConnected) viewModel.updateGenresList(isConnected)
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupErrorFoundObserver() {
        viewModel.errorFound.observe(viewLifecycleOwner, {
            if (it) {
                val action =
                    CollectionFragmentDirections.actionCollectionFragmentToSystemFailedFragment()
                findNavController().safeNavigate(action)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchField = searchItem.actionView as EditText
        searchField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                charQuery = s.toString()
                      val action =
                          CollectionFragmentDirections.actionCollectionFragmentToSearchResultFragment(
                              local = "remote")
                      findNavController().safeNavigate(action)
            }
        })
    }

    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }
}


