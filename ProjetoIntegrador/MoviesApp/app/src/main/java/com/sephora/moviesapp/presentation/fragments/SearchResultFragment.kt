package com.sephora.moviesapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sephora.moviesapp.R
import com.sephora.moviesapp.data.model.DetailedMovieEntity
import com.sephora.moviesapp.data.model.GenreListEntity
import com.sephora.moviesapp.databinding.FragmentSearchResultBinding
import com.sephora.moviesapp.presentation.adapters.*
import com.sephora.moviesapp.presentation.viewmodels.SearchResultsViewModel
import com.sephora.moviesapp.utils.Functions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val viewModel by viewModels<SearchResultsViewModel>()
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreListAdapter: GenreAdapter
    private lateinit var searchAdapter: MoviesAdapter
    private lateinit var searchField: EditText
    private var count = 0
    private val args: SearchResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        _binding = FragmentSearchResultBinding.bind(view)

        binding.apply {
            btnHome.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        searchAdapter = MoviesAdapter(
            { movie -> showMovieDetails(movie) },
            { movieId, isFavorite -> changeFavoriteStatus(movieId, isFavorite) })


        binding.apply {
            rvItemModel.setHasFixedSize(true)
            rvItemModel.adapter = searchAdapter
        }

        binding.rvItemModel.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvBtnGenre.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )

        genreListAdapter = GenreAdapter(mutableListOf()) { genre -> showByGenre(genre) }
        binding.rvBtnGenre.adapter = genreListAdapter

        //   viewModel.searchMovie(query = args.query, source = args.local)
        //     setupObserveResultsList()

        // setupErrorFoundObserver()

        //   viewModel.getGenresList()
        //    setupObserveGenresList()

    }

    private fun changeFavoriteStatus(movieId: Int, isFavorite: Boolean) {

    }

    private fun showMovieDetails(movie: DetailedMovieEntity?) {

        if (movie == null) {
            binding.imgNotFound.visibility = VISIBLE
            binding.txtNotFound.visibility = VISIBLE
            binding.txtNotFoundMsg.visibility = VISIBLE
            binding.layoutMovieFound.visibility = INVISIBLE
        } else {
            val action =
                SearchResultFragmentDirections.actionSearchResultFragmentToMovieDetailFragment2(
                    movieId = movie.movieId,
                    local = args.local
                )
            findNavController().navigate(action)
        }
    }


    private fun showByGenre(genre: GenreListEntity.GenreEntity?) {
        // viewModel.getMoviesByCategory(genre)
        // setupObserveMoviesByGenre(genre)
    }

    fun setupObserveFavoritesList() {
        viewModel.foundFavorite.observe(
            viewLifecycleOwner, {
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                searchAdapter.notifyDataSetChanged()
                binding.imgNotFound.visibility = INVISIBLE
                binding.txtNotFound.visibility = INVISIBLE
                binding.txtNotFoundMsg.visibility = INVISIBLE
                binding.layoutMovieFound.visibility = VISIBLE
            }
        )
    }

    fun setupObserveResultsList() {
        viewModel.foundMovies.observe(
            viewLifecycleOwner, {
                binding.imgNotFound.visibility = INVISIBLE
                binding.txtNotFound.visibility = INVISIBLE
                binding.txtNotFoundMsg.visibility = INVISIBLE
                binding.layoutMovieFound.visibility = VISIBLE
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                searchAdapter.notifyDataSetChanged()
            }
        )
    }

    fun setupObserveGenresList() {
        viewModel.genreList.observe(viewLifecycleOwner, {

            genreListAdapter.dataset.addAll(it.genreResults)
            genreListAdapter.notifyDataSetChanged()

        }
        )
    }

    /*
    fun setupErrorFoundObserver() {
        viewModel.errorFound.observe(viewLifecycleOwner, {
            Log.d("qual é", "$it")
            if (it == true) {
                val action =
                    CollectionFragmentDirections.actionCollectionFragmentToSystemFailedFragment()
                findNavController().navigate(action)
            }
        })
    }
*/
    fun checkNetWorkStatus() {
        val status: Functions = Functions()
        if (!status.checkNetworkStatus(requireContext())) {
            val action =
                CollectionFragmentDirections.actionCollectionFragmentToSystemFailedFragment()
            findNavController().navigate(action)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        searchField = searchItem.actionView as EditText

        searchField.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable) {
                if (s.isBlank()) {
                    searchField.clearFocus()
                    requireActivity().onBackPressed()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                val position: Int = searchField.length()
                searchField.setSelection(position)

                if (args.local == "local") {
                    viewModel.searchMovie(s)
                    setupObserveFavoritesList()
                } else {
                    viewModel.searchMovie(s)
                    setupObserveResultsList()
                }

            }
        })
        searchField.setText(args.query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        count = 0
        _binding = null
    }
}

