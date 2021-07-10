package com.example.myhealth.view

import androidx.lifecycle.LiveData
import com.example.myhealth.model.AppState
import com.example.myhealth.model.HealthRepository
import com.example.myhealth.view.activity.BaseViewModel

class HealthListViewModel(val provider : HealthRepository): BaseViewModel<AppState>() {

    fun subscribe(): LiveData<AppState> = liveDataViewModel

    override fun errorReturned(t: Throwable) {}
}