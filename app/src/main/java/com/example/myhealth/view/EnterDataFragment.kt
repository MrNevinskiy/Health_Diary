package com.example.myhealth.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myhealth.R
import com.example.myhealth.databinding.FragmentEnterDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class EnterDataFragment : Fragment(R.layout.fragment_enter_data) {

    private val viewModel: EnterDataViewModel by viewModel()
    private lateinit var binding: FragmentEnterDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribe()
        init()
    }

    private fun init(){

    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM, yyyy")
        return formatter.format(date)
    }

}