package com.example.eventplanner.View.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.*
import com.example.eventplanner.databinding.ActivityMainBinding
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbEvent = EventDatabase

        dbEvent.getEvents()
        dbEvent.getEventsOnce()
        val fm = supportFragmentManager

        binding.bottomNavigationView.setOnItemSelectedListener {

            Log.i(TAG, "Item Selected on bottom navigation bar")

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
        Log.i(TAG, "Replace Fragment called")

        fm.beginTransaction().apply {
            replace(R.id.fragmentHolder, fragment)
            commit()
        }
    }

}