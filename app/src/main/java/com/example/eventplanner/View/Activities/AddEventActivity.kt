package com.example.eventplanner.View.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.viewModel.AddEventViewModel
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
    private var isDone : Boolean = false
    private lateinit var viewModel: AddEventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getParcelableExtra<Event>("event")
        viewModel = ViewModelProvider(this)[AddEventViewModel::class.java]
        setContentView(R.layout.activity_add_event)
        var intent = Intent(Intent.ACTION_MAIN)


        name = findViewById(R.id.name)
        date = findViewById(R.id.date)
        description = findViewById(R.id.description)
        location = findViewById(R.id.location)
        categories = findViewById(R.id.categories)
        time  = findViewById<EditText>(R.id.time)
        done = findViewById(R.id.done)
        notDone = findViewById(R.id.notDone)
        btnAddEvent = findViewById(R.id.btnAddEvent)

        if(data != null){
            setText(data)
        }

        viewModel.categoryList.observe(this, androidx.lifecycle.Observer {
            catList ->
            var categoryNameList : MutableList<String>  = mutableListOf()
            for (i in catList){
                categoryNameList.add(i.name)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, categoryNameList
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categories.adapter = adapter
        })

        btnAddEvent.setOnClickListener() {
            if (viewModel.inputValidation(name, date, location, time)) {
                if (done.isSelected) {
                    isDone = true
                }
                val splitDate = date.text.toString().split("-")
                val splitTime = time.text.toString().split(":")
                var id = ""
                if (data != null) {
                    id = data.id
                }
                val event = Event(
                    name.text.toString(),
                    Date(
                        splitDate[2].toInt(),
                        splitDate[1].toInt(),
                        splitDate[0].toInt(),
                        splitTime[0].toInt(),
                        splitTime[1].toInt()
                    ),
                    "",
                    description.text.toString(),
                    location.text.toString(),
                    isDone,
                    id
                )
                intent.putExtra("event", event)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun setText(event: Event){
            name.text = event.name
            date.text = "${event.date.date}-${event.date.month}-${event.date.year}"
            location.text = event.location
            time.text = "${event.date.hours}:${event.date.minutes}"
            description.text = event.description
            done.isSelected = event.done
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