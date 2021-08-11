package com.sephora.moviesapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sephora.moviesapp.R
import com.sephora.moviesapp.data.model.DetailedMovieEntity
import com.sephora.moviesapp.databinding.FragmentSearchResultBinding
import com.sephora.moviesapp.presentation.adapters.*
import com.sephora.moviesapp.presentation.viewmodels.SearchResultsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val viewModel by viewModels<SearchResultsViewModel>()
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreListAdapter: GenreAdapter
    private lateinit var searchAdapter: MoviesAdapter
    private lateinit var searchField: EditText
    private var query : CharSequence = ""
    private val args: SearchResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        _binding = FragmentSearchResultBinding.bind(view)

        binding.apply {
            btnHome.setOnClickListener {
                view?.hideKeyboard()
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
            requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvBtnGenre.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        genreListAdapter = GenreAdapter(mutableListOf()) { genre -> showByGenre(genre) }
        binding.rvBtnGenre.adapter = genreListAdapter

        viewModel.checkNetWorkStatus(requireContext())
        setupErrorFoundObserver()

        viewModel.getGenresList()
        setupObserveGenresList()
    }

    private fun changeFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        viewModel.changeFavoriteStatus(movieId, isFavorite)
        viewModel.filterByGenre(-1, "")
    }

    private fun showMovieDetails(movie: DetailedMovieEntity) {
            val action =
                SearchResultFragmentDirections.actionSearchResultFragmentToMovieDetailFragment2(
                    movieId = movie.movieId, local = args.local)
            findNavController().navigate(action)
    }

    private fun showByGenre(genreId: Int) {
        viewModel.filterByGenre(genreId, query)
        if(args.local == "local") setupObserveFavoritesList()
        else setupObserveResultsList()
    }

    fun setupObserveFavoritesList() {
        viewModel.foundFavorite.observe(
            viewLifecycleOwner, {
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                searchAdapter.notifyDataSetChanged()
            })
        checkResultIsFound()
    }

    @ExperimentalCoroutinesApi
    fun setupObserveResultsList() {
        viewModel.foundMovies.observe(
            viewLifecycleOwner, {
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                searchAdapter.notifyDataSetChanged()
            })
        checkResultIsFound()
    }

    fun setupObserveGenresList() {
        viewModel.genreList.observe(viewLifecycleOwner, {
            genreListAdapter.dataset.addAll(it)
            genreListAdapter.notifyDataSetChanged()
        })
    }

    fun setupErrorFoundObserver() {
        viewModel.errorFound.observe(viewLifecycleOwner, {
            if (it) {
                val action = SearchResultFragmentDirections.actionSearchResultFragmentToSystemFailedFragment()
                findNavController().navigate(action)
            }
        })
    }

    fun checkResultIsFound() {
        if (searchAdapter.itemCount < 1) {
            binding.imgNotFound.visibility = VISIBLE
            binding.txtNotFound.visibility = VISIBLE
            binding.txtNotFoundMsg.visibility = VISIBLE
            binding.rvBtnGenre.visibility = INVISIBLE
            binding.rvItemModel.visibility = INVISIBLE
        } else {
            binding.imgNotFound.visibility = INVISIBLE
            binding.txtNotFound.visibility = INVISIBLE
            binding.txtNotFoundMsg.visibility = INVISIBLE
            binding.rvBtnGenre.visibility = VISIBLE
            binding.rvItemModel.visibility = VISIBLE
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
                    view?.hideKeyboard()
                   requireActivity().onBackPressed()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                val position: Int = searchField.length()
                searchField.setSelection(position)

                if (args.local == "local") {
                    query = s
                    viewModel.filterByGenre(-1, s)
                    setupObserveFavoritesList()
                } else {
                    query = s
                    viewModel.filterByGenre(-1, s)
                    setupObserveResultsList()
                }
            }
        })
        searchField.setText(args.query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

