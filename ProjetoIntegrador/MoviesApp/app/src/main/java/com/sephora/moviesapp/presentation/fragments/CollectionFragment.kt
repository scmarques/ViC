package com.sephora.moviesapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sephora.moviesapp.R
import com.sephora.moviesapp.databinding.FragmentCollectionFragmentsBinding
import com.sephora.moviesapp.presentation.adapters.ScreenAdapter
import com.sephora.moviesapp.presentation.viewmodels.CollectionFragmentViewModel
import com.sephora.moviesapp.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : Fragment(R.layout.fragment_collection_fragments){

    private val viewModel by viewModels<CollectionFragmentViewModel>()
    private lateinit var binding: FragmentCollectionFragmentsBinding
    private lateinit var tablayout : TabLayout
    private lateinit var viewpager : ViewPager2


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCollectionFragmentsBinding.bind(view)
        viewpager = binding.fragViewPager
        viewpager.adapter = ScreenAdapter(childFragmentManager, lifecycle)
        tablayout = binding.mainTablayout
        viewpager.isUserInputEnabled = false

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            when (position) {
                1 -> {
                    tab.text = getString(R.string.favorites)
                }
                else -> {
                    tab.text = getString(R.string.all_movies)
                }
            }
        }.attach()

        if (!viewModel.checkNetWorkStatus(requireContext())) {
            val action =
                CollectionFragmentDirections.actionCollectionFragmentToSystemFailedFragment()
            findNavController().safeNavigate(action)
        }
        setupErrorObserver()
        setHasOptionsMenu(true)
    }

    fun setupErrorObserver(){
        viewModel.checkNetWorkStatus(requireContext())
        viewModel.errorFound.observe(
            viewLifecycleOwner, {
                if (it) {
                    tablayout.setScrollPosition(1, 0f, true)
                    viewpager.currentItem = 1
                }
            }
        )
    }


}