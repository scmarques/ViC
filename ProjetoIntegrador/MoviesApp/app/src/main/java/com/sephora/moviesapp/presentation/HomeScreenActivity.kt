package com.sephora.moviesapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.sephora.moviesapp.R
import com.sephora.moviesapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.tbMenu.title = ""
        setSupportActionBar(binding.tbMenu)
       // setupActionBarWithNavController(navController)


    //    supportActionBar?.apply {
     //       this.elevation = 15F
    //    }
    }
    }


      //  binding.tbMenu.setDisplayShowHomeEnabled(true)


/*
        override fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp() || super.onSupportNavigateUp()
        }*/

