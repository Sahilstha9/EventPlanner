package com.example.eventplanner.View.Activities

import android.accounts.AuthenticatorDescription
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.eventplanner.R
import com.example.eventplanner.viewModel.AddCategoryViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class AddCategoryActivity : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var description : TextView
    private lateinit var btnAddCategory : Button
    private lateinit var viewModel : AddCategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        viewModel = AddCategoryViewModel()

        val intent = Intent(Intent.ACTION_MAIN)

        name = findViewById(R.id.name)
        description = findViewById(R.id.description)
        btnAddCategory = findViewById(R.id.btnAddCategory)

        btnAddCategory.setOnClickListener(){
            if(viewModel.inputValidation(name)) {
                val category = Category(name.text.toString(), description.text.toString())
                intent.putExtra("category", category)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
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