package com.example.eventplanner.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase
import com.example.eventplanner.R
import com.example.eventplanner.view.fragments.*
import com.example.eventplanner.databinding.ActivityMainBinding
import com.example.eventplanner.modal.AuthenticationModal
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private val rotateOpen : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}
    private val textFadeIn : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.text_fade_in)}
    private val textFadeOut : Animation by lazy{AnimationUtils.loadAnimation(this, R.anim.text_fade_out)}
    private val insightsFragment = InsightsFragment()
    private val listFragment = ListFragment()
    private val calendarFragment = CalendarFragment()
    private val categoryFragment = CategoryListFragment()
    private val profileFragment = ProfileFragment()
    private var clicked = false
    private var dbEvent = EventDatabase
    private var dbCategory = CategoryDatabase

    private val getEvent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val parcel : Event = intent?.getParcelableExtra("event")!!
            Log.i(TAG, parcel.name)

            dbEvent.createEvent(parcel, findViewById(R.id.coordinator), intent?.getParcelableExtra<Uri>("image"))
        }
    }

    private val getCategory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            val parcel = intent?.getParcelableExtra<Category>("category")

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

        binding.fragmentHolder.setOnClickListener{
            if(clicked){
                setVisibility(clicked)
                setAnimation(clicked)
                clicked = !clicked
            }
        }


        binding.bottomNavigationView.setOnItemSelectedListener {

            Log.i(TAG, "Item Selected on bottom navigation bar")

            when (it.itemId){
                R.id.profile -> replaceFragment(fm, profileFragment)
                R.id.insights -> replaceFragment(fm, insightsFragment)
                R.id.calendar -> replaceFragment(fm, calendarFragment)
                R.id.events -> replaceFragment(fm, listFragment)
                R.id.add -> replaceFragment(fm, categoryFragment)

                else ->{

                }

            }
            true
        }
        binding.addBtn.bringToFront()
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
        if(clicked){
            binding.fragmentHolder.alpha = 0.2F
        }
        else{
            binding.fragmentHolder.alpha = 1F
        }
    }

    private fun setAnimation(clicked : Boolean) {
        if(!clicked){
            binding.addEvent.startAnimation(fromBottom)
            binding.addCategory.startAnimation(fromBottom)
            binding.addBtn.startAnimation(rotateOpen)
            binding.textAddEvent.startAnimation(textFadeIn)
            binding.textAddCategory.startAnimation(textFadeIn)
        }
        else{
            binding.addEvent.startAnimation(toBottom)
            binding.addCategory.startAnimation(toBottom)
            binding.addBtn.startAnimation(rotateClose)
            binding.textAddEvent.startAnimation(textFadeOut)
            binding.textAddCategory.startAnimation(textFadeOut)
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