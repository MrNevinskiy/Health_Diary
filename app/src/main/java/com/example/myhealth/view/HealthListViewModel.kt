package com.example.myhealth.view

import androidx.lifecycle.LiveData
import com.example.myhealth.model.AppState
import com.example.myhealth.model.HealthRepository
import com.example.myhealth.view.activity.BaseViewModel
import kotlinx.coroutines.launch

class HealthListViewModel(val provider : HealthRepository): BaseViewModel<AppState>() {

    fun subscribe(): LiveData<AppState> = liveDataViewModel

    fun getHealth() {
        viewModelCoroutineScope.launch {
            liveDataViewModel.value = AppState.Success(provider.getHealth())
        }
    }

    override fun errorReturned(t: Throwable) {}
}