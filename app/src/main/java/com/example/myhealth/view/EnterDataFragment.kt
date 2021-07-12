package com.example.myhealth.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myhealth.R
import com.example.myhealth.databinding.FragmentEnterDataBinding
import com.example.myhealth.model.data.HealthData
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class EnterDataFragment : Fragment(R.layout.fragment_enter_data) {

    private val viewModel: EnterDataViewModel by viewModel()
    private lateinit var binding: FragmentEnterDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterDataBinding.bind(view)
        viewModel.subscribe()
        saveNewData()
        init()
    }

    private fun init(){
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnYes.setOnClickListener {
            saveNewData()
            findNavController().navigate(R.id.action_enterDataFragment_to_healthListFragment)
        }
    }

    private fun saveNewData(){
        val presDay = binding.bloodPressureEditText.text
        val pulseDay = binding.pulseEditText.text
        val presNight = binding.bloodPressureNightEditText.text
        val pulseNight = binding.pulseNightEditText.text
        val data = HealthData(
            date = getCurrentDate(),
            pressureDay = presDay.toString(),
            timeDay = getCurrentTime(),
            timeNight = getCurrentTime(),
            pulseDay = pulseDay.toString(),
            pressureNight = presNight.toString(),
            pulseNight = pulseNight.toString()
        )
        viewModel.saveData(data)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM, yyyy")
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(date)
    }

}