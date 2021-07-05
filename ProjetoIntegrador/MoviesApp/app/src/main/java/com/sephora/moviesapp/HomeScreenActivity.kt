package com.sephora.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeScreenActivity : AppCompatActivity() {

    lateinit var txt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        val viewpager = findViewById<ViewPager2>(R.id.frag_view_pager)
        viewpager.adapter = ScreenAdapter(supportFragmentManager, lifecycle)
        val tablayout = findViewById<TabLayout>(R.id.main_tablayout)

        TabLayoutMediator(tablayout, viewpager){tab, position ->
            when (position){
                0->{
                    tab.text="Todos os filmes"
                }
                1->{
                    tab.text="Favoritos"
                }
            }
        }.attach()


    }

}