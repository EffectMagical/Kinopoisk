package com.example.kinopoisk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val innovationsFragment = InnovationsFragment()
        val searchFragment = SearchFragment()
        val favouriteFragment = FavouriteFragment()

        setCurrentFragment(innovationsFragment)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.innovations_menu_item -> setCurrentFragment(innovationsFragment)
                R.id.search_menu_item -> setCurrentFragment(searchFragment)
                R.id.favorites_menu_item -> setCurrentFragment(favouriteFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}