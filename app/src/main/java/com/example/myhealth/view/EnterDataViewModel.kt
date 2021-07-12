package com.example.myhealth.view

import androidx.lifecycle.LiveData
import com.example.myhealth.model.AppState
import com.example.myhealth.model.HealthRepository
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.view.activity.BaseViewModel
import kotlinx.coroutines.launch

class EnterDataViewModel(private val provider: HealthRepository) : BaseViewModel<AppState>() {

    fun subscribe(): LiveData<AppState> = liveDataViewModel

    fun saveData(data: HealthData) {
        viewModelCoroutineScope.launch {
            provider.saveNote(data)
        }
    }

    override fun errorReturned(t: Throwable) {}
}