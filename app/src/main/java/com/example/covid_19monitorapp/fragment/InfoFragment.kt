package com.example.covid_19monitorapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.covid_19monitorapp.R
import kotlinx.android.synthetic.main.dialog_info.*

class InfoFragment: DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var counter: Int = 10

        hints.setOnClickListener(){
            if(counter > 1) counter--
            else Toast.makeText(activity, "Have a good day!", Toast.LENGTH_LONG).show()
        }

        closeInfo.setOnClickListener(){
            dismiss()
        }
    }
}