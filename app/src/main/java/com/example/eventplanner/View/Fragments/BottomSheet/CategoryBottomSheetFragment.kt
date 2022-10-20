package com.example.eventplanner.View.Fragments.BottomSheet

import android.accounts.AuthenticatorDescription
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.eventplanner.R
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheetFragment(item : Category) : BottomSheetDialogFragment(){
    private lateinit var name : TextView
    private lateinit var description : TextView
    private var category : Category = item
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(R.id.bottom)
            bottomSheetInternal?.minimumHeight= 300
        }
        name.text = "Name : ${category.name}"
        description.text = category.description
    }
}