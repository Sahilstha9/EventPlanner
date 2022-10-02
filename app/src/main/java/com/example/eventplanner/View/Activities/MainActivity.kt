package com.example.eventplanner.View.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.*
import com.example.eventplanner.databinding.ActivityMainBinding
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

//    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //binding = ActivityMainBinding.inflate(layoutInflater)

        var fm = supportFragmentManager

        var bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavBar.setOnItemReselectedListener {
            when (it.itemId){
                R.id.profile -> replaceFragment(fm, ProfileFragment())
                R.id.insights -> replaceFragment(fm, InsightsFragment())
                R.id.calendar -> replaceFragment(fm, CalendarFragment())
                R.id.events -> replaceFragment(fm, ListFragment())
                R.id.add -> replaceFragment(fm, AddFragment())

                else ->{

                }

            }
            true
        }
    }

    fun replaceFragment(fm : FragmentManager, fragment : Fragment){
        fm.beginTransaction().apply {
            replace(R.id.fragmentHolder, fragment)
            commit()
        }
    }

}