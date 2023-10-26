package com.example.eventplanner.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.viewModel.AddCategoryViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddCategoryActivity : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var description : TextView
    private lateinit var btnAddCategory : Button
    private lateinit var viewModel : AddCategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        val data = intent.getParcelableExtra<Category>("category")
        viewModel = ViewModelProvider(this)[AddCategoryViewModel::class.java]
        val intent = Intent(Intent.ACTION_MAIN)

        name = findViewById(R.id.name)
        description = findViewById(R.id.description)
        btnAddCategory = findViewById(R.id.btnAddCategory)

        if(data != null){
            setText(data)
        }

        btnAddCategory.setOnClickListener(){
            if(viewModel.inputValidation(name)) {
                val category = Category(name.text.toString(), description.text.toString(), data?.id ?: "", userId = viewModel.getUser())
                intent.putExtra("category", category)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun setText(category: Category){
        name.text = category.name
        description.text = category.description
    }

    /**
     * displays a warning on back pressed before exiting the activity
     */
    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Warning")
            .setMessage("You will lose all your saved changes\nAre you sure?")
            .setNegativeButton("No"){ _, _ ->

            }
            .setPositiveButton("Yes"){ _, _ ->
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
            .show()
    }
}