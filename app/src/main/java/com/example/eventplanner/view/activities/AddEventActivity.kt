package com.example.eventplanner.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.viewModel.AddEventViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*


class AddEventActivity : AppCompatActivity() {

    private val TAG: String = "AddEventActivity"
    private lateinit var name : TextView
    private lateinit var date: TextView
    private lateinit var time: TextView
    private lateinit var description : TextView
    private lateinit var location : TextView
    private lateinit var categories : Spinner
    private lateinit var btnAddEvent : Button
    private lateinit var done : RadioButton
    private lateinit var notDone : RadioButton
    private var isDone : Boolean = true
    private lateinit var img : ImageView
    private var imageSelected = false
    private lateinit var btnImage : Button
    private var categoryList = mutableListOf<Category>()
    private lateinit var viewModel: AddEventViewModel
    private var image : Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        if (it != null){
            imageSelected = true
            img.setImageURI(it)
            image = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getParcelableExtra<Event>("event")
        viewModel = ViewModelProvider(this)[AddEventViewModel::class.java]
        setContentView(R.layout.activity_add_event)
        val intent = Intent(Intent.ACTION_MAIN)


        name = findViewById(R.id.name)
        date = findViewById(R.id.date)
        description = findViewById(R.id.description)
        location = findViewById(R.id.location)
        categories = findViewById(R.id.categories)
        time  = findViewById<EditText>(R.id.time)
        done = findViewById(R.id.done)
        notDone = findViewById(R.id.notDone)
        btnAddEvent = findViewById(R.id.btnAddEvent)
        btnImage = findViewById(R.id.getImage)
        img = findViewById(R.id.img)

        if(data != null){
            setText(data)
        }

        viewModel.categoryList.observe(this, androidx.lifecycle.Observer {
            catList ->
            val categoryNameList : MutableList<String>  = mutableListOf()
            for (i in catList){
                categoryNameList.add(i.name)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, categoryNameList
            )
            categoryList = catList
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categories.adapter = adapter
        })

        btnImage.setOnClickListener{
            getContent.launch("image/*")
        }

        btnAddEvent.setOnClickListener() {
            Log.i("New", done.isChecked.toString())
            var c : Category? = null
            for (i in categoryList){
                if(i.name == categories.selectedItem.toString()){
                    c = i
                }
            }
            if (viewModel.inputValidation(name, date, location, time)) {
                isDone = done.isChecked
                val splitDate = date.text.toString().split("-")
                val splitTime = time.text.toString().split(":")
                val event = Event(
                    name.text.toString(),
                    Date(
                        splitDate[2].toInt(),
                        splitDate[1].toInt() - 1 ,
                        splitDate[0].toInt(),
                        splitTime[0].toInt(),
                        splitTime[1].toInt()
                    ),
                    c?.id ?: "",
                    description.text.toString(),
                    location.text.toString(),
                    isDone,
                    id = data?.id ?: ""
                )
                intent.putExtra("event", event)
                intent.putExtra("image", image)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }


    private fun setText(event: Event){
            name.text = event.name
            date.text = "${event.date.date}-${event.date.month + 1}-${event.date.year}"
            location.text = event.location
            time.text = "${event.date.hours}:${event.date.minutes}"
            description.text = event.description
            done.isChecked = event.done
    }




    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Warning")
            .setMessage("You will lose all your saved changes\nAre you sure?")
            .setNegativeButton("No"){ dialog, which->

            }
            .setPositiveButton("Yes"){dialog, which->
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
            .show()
    }
}