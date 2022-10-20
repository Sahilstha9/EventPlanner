package com.example.eventplanner.View.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.*
import com.example.eventplanner.databinding.ActivityMainBinding
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private val rotateOpen : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}
    private var clicked = false
    private var dbEvent = EventDatabase
    private var dbCategory = CategoryDatabase

    val getEvent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            var intent = it.data
            var parcel : Event = intent?.getParcelableExtra("event")!!
            Log.i(TAG, parcel.name)

            dbEvent.createEvent(parcel, findViewById(R.id.coordinator))
        }
    }

    val getCategory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            var intent = it.data
            var parcel = intent?.getParcelableExtra<Category>("category")

            dbCategory.createCategory(parcel!!, findViewById(R.id.coordinator))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val eventActivityIntent = Intent(this, AddEventActivity::class.java)
        val categoryActivityIntent = Intent(this, AddCategoryActivity::class.java)

        binding.addBtn.setOnClickListener(){
            onAddButtonClicked()
        }

        binding.addEvent.setOnClickListener(){
            getEvent.launch(eventActivityIntent)
        }

        binding.addCategory.setOnClickListener(){
            getCategory.launch(categoryActivityIntent)
        }


        val fm = supportFragmentManager

        binding.bottomNavigationView.setOnItemSelectedListener {

            Log.i(TAG, "Item Selected on bottom navigation bar")

            when (it.itemId){
                R.id.profile -> replaceFragment(fm, ProfileFragment())
                R.id.insights -> replaceFragment(fm, InsightsFragment())
                R.id.calendar -> replaceFragment(fm, CalendarFragment())
                R.id.events -> replaceFragment(fm, ListFragment())
                R.id.add -> replaceFragment(fm, CategoryListFragment())

                else ->{

                }

            }
            true
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked : Boolean) {
        if(!clicked){
            binding.addEvent.startAnimation(fromBottom)
            binding.addCategory.startAnimation(fromBottom)
            binding.addBtn.startAnimation(rotateOpen)
        }
        else{
            binding.addEvent.startAnimation(toBottom)
            binding.addCategory.startAnimation(toBottom)
            binding.addBtn.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked : Boolean) {
        if(!clicked){
            binding.addEvent.visibility = View.VISIBLE
            binding.addCategory.visibility = View.VISIBLE
            binding.textAddEvent.visibility = View.VISIBLE
            binding.textAddCategory.visibility = View.VISIBLE
        }
        else{
            binding.addEvent.visibility = View.INVISIBLE
            binding.addCategory.visibility = View.INVISIBLE
            binding.textAddEvent.visibility = View.INVISIBLE
            binding.textAddCategory.visibility = View.INVISIBLE
        }
    }

    private fun replaceFragment(fm : FragmentManager, fragment : Fragment){
        Log.i(TAG, "Replace Fragment called")

        fm.beginTransaction().apply {
            replace(R.id.fragmentHolder, fragment)
            commit()
        }
    }

}