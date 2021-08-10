package com.sephora.moviesapp.presentation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.tabs.TabLayoutMediator
import com.sephora.moviesapp.R
import com.sephora.moviesapp.databinding.FragmentCollectionFragmentsBinding
import com.sephora.moviesapp.presentation.adapters.ScreenAdapter
import com.sephora.moviesapp.presentation.viewmodels.SharedScreenViewModel
import com.sephora.moviesapp.utils.Functions

@ExperimentalPagingApi
class CollectionFragment : Fragment(R.layout.fragment_collection_fragments){

    private lateinit var binding: FragmentCollectionFragmentsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCollectionFragmentsBinding.bind(view)

        val viewpager = binding.fragViewPager
        viewpager.adapter = ScreenAdapter(childFragmentManager, lifecycle)
        val tablayout = binding.mainTablayout
        viewpager.isUserInputEnabled = false

        if(!checkNetWorkStatus()){
            tablayout.setScrollPosition(1, 0f, true)
            viewpager.currentItem = 1
        }

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            when (position) {
                1 -> {
                    tab.text = "Favoritos"
                }
                else -> {
                    tab.text = "Todos os filmes"
                }
            }
        }.attach()

      //  ((tablayout.getTabAt(2)?.view))?.isVisible = false

        setHasOptionsMenu(true)
    }



    fun checkNetWorkStatus() : Boolean {
        val status = Functions()
        if(!status.checkNetworkStatus(requireContext())){
            //val action = CollectionFragmentDirections.actionCollectionFragmentToSystemFailedFragment()
            //findNavController().navigate(action)
            return false
        }
        return true
    }

}