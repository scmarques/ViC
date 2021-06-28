package com.sandyara.aula18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
/*Foram feitas mudanças nos nomes das variáveis e classes
    Foi utilizado viewpager2
    Alteração no xml trocando os textos para sp e acrescentando constraints com dimensões menores que 64
 */
class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        val viewpager = findViewById<ViewPager2>(R.id.frag_view_pager)
        viewpager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        val tablayout = findViewById<TabLayout>(R.id.main_tablayout)

        TabLayoutMediator(tablayout, viewpager){tab, position ->
            when (position){
                0->{
                    tab.text="Primeiro"
                }
                1->{
                    tab.text="Segundo"
                }
            }
        }.attach()

    }
}