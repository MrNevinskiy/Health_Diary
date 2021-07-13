package com.example.myhealth.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myhealth.R
import com.example.myhealth.databinding.FragmentEnterDataBinding
import com.example.myhealth.model.data.HealthData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class EnterDataFragment : DialogFragment(R.layout.fragment_enter_data) {

    @ObsoleteCoroutinesApi
    private val healthDataModel by lazy { requireParentFragment().getViewModel<HealthListViewModel>() }

    private lateinit var binding: FragmentEnterDataBinding

    companion object{
        fun newInstance() = EnterDataFragment()
    }

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterDataBinding.bind(view)
        saveNewData()
        init()
    }

    @ObsoleteCoroutinesApi
    private fun init(){
        binding.btnCancel.setOnClickListener {
            closeDialog()
        }
        binding.btnYes.setOnClickListener {
            saveNewData()
            closeDialog()
        }
    }

    @ObsoleteCoroutinesApi
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
        healthDataModel.dialogFragmentBtnYesClicked(data)
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

    private fun closeDialog() {
        this.dismiss()
    }

}