package com.example.myhealth.view.splash

import com.example.myhealth.model.repo.IHealthRepository
import com.example.myhealth.model.exceptions.NoAuthExceptions
import com.example.myhealth.view.base.BaseViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

class SplashViewModel (val repository: IHealthRepository<Any>) : BaseViewModel<Boolean>() {

    @ObsoleteCoroutinesApi
    fun requestUser() = launch {
        repository.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthExceptions())
    }

}