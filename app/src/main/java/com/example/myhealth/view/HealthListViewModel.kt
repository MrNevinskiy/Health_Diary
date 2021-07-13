package com.example.myhealth.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhealth.model.AppState
import com.example.myhealth.model.repo.HealthRepository
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.view.base.BaseViewModel
import com.example.myhealth.view.utils.Event
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router

class HealthListViewModel(val provider: HealthRepository) :  BaseViewModel<List<HealthData>?>() {

    private val repositoryHealthData = provider.getHealthData()
    val openDialogFragmentLiveData: LiveData<Event<HealthData?>>
        get() = _openDialogFragmentLiveData
    private val _openDialogFragmentLiveData = MutableLiveData<Event<HealthData?>>()


    init {
        launch {
            repositoryHealthData.consumeEach { result ->
                when (result){
                    is AppState.Success<*> -> setData(result.data as? List<HealthData>)
                    is AppState.Error -> setError(result.error)
                }
            }
        }
    }

    fun dialogFragmentBtnYesClicked(healthData: HealthData) {
        launch {
            provider.saveHealthData(healthData)
        }
    }

    fun toSaveData() {
        _openDialogFragmentLiveData.value = Event(null)
    }

}